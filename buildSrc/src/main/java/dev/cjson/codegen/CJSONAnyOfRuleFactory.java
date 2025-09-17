package dev.cjson.codegen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.sun.codemodel.*;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.*;
import org.jsonschema2pojo.util.ParcelableHelper;

import java.util.List;

public class CJSONAnyOfRuleFactory extends RuleFactory {

    @Override
    public Rule<JPackage, JClass> getArrayRule() {
        return new CJSONAnyOfArrayRule(this);
    }

    @Override
    public Rule<JPackage, JType> getObjectRule() {
        return new ContentBlockObjectRule(this);
    }

    @Override
    public Rule<JDefinedClass, JDefinedClass> getPropertyRule() {
        return new ConstToEnumPropertyRule(this);
    }

    /* ---------- helpers ---------- */

    JClass ensureContentBlockInterface(JPackage basePkg, GenerationConfig cfg) {
        String pkgName = ((cfg.getTargetPackage() != null && !cfg.getTargetPackage().isEmpty())
                ? cfg.getTargetPackage() : basePkg.name()) + ".conversation";
        JCodeModel cm = basePkg.owner();
        JPackage p = cm._package(pkgName);
        JDefinedClass iface;
        try {
            iface = p._interface("ContentBlock");
            iface.javadoc().add("Marker interface for all content block types.");
        } catch (IllegalArgumentException | JClassAlreadyExistsException e) {
            iface = p._getClass("ContentBlock");
        }
        return iface;
    }

    JClass ensureMessageInterface(JPackage basePkg, GenerationConfig cfg) {
        String pkgName = ((cfg.getTargetPackage() != null && !cfg.getTargetPackage().isEmpty())
                ? cfg.getTargetPackage() : basePkg.name()) + ".conversation";
        JCodeModel cm = basePkg.owner();
        JPackage p = cm._package(pkgName);
        JDefinedClass iface;
        try {
            iface = p._interface("Message");
            iface.javadoc().add("Marker interface for all message types.");
        } catch (IllegalArgumentException | JClassAlreadyExistsException e) {
            iface = p._getClass("Message");
        }
        return iface;
    }

    static final List<String> CONTENT_BLOCK_NAMES = List.of("TextBlock", "ThinkingBlock", "ToolApprovalBlock", "ToolCallBlock", "ToolResultBlock");

    static final List<String> MESSAGE_NAMES = List.of("CompositeMessage", "TextMessage");

    static boolean isBlockLike(String simpleName) {
        return CONTENT_BLOCK_NAMES.contains(simpleName);
    }

    static boolean isMessageLike(String simpleName) {
        return MESSAGE_NAMES.contains(simpleName);
    }

    static String simpleNameFromRef(String ref) {
        if (ref == null) return "Choice";
        int idx = ref.lastIndexOf('/');
        return (idx >= 0 && idx + 1 < ref.length()) ? ref.substring(idx + 1) : ref;
    }

    static Schema rootOf(Schema s) {
        Schema cur = s;
        while (cur.getParent() != null && cur != cur.getParent()) cur = cur.getParent();
        return cur;
    }

    /* ---------- rules ---------- */

    /**
     * Ensures *Block types implement ContentBlock.
     */
    static class ContentBlockObjectRule extends ObjectRule {

        private final RuleFactory rf;

        ContentBlockObjectRule(RuleFactory rf) {
            super(rf, new ParcelableHelper(), rf.getReflectionHelper());
            this.rf = rf;
        }

        @Override
        public JType apply(String nodeName, JsonNode node, JsonNode parent, JPackage jpackage, Schema schema) {
            JType jt = super.apply(nodeName, node, parent, jpackage, schema);

            if (jt instanceof JDefinedClass cls) {
                CJSONAnyOfRuleFactory ruleFactory = (CJSONAnyOfRuleFactory) rf;
                if (isBlockLike(cls.name())) {
                    JClass cb = ruleFactory.ensureContentBlockInterface(jpackage, rf.getGenerationConfig());
                    cls._implements(cb);
                }
                if (isMessageLike(cls.name())) {
                    JClass cb = ruleFactory.ensureMessageInterface(jpackage, rf.getGenerationConfig());
                    cls._implements(cb);
                }
            }
            return jt;
        }
    }

    /**
     * Generates branch classes and types contentBlocks as List<ContentBlock>.
     */
    static class CJSONAnyOfArrayRule extends ArrayRule {
        private final RuleFactory rf;

        CJSONAnyOfArrayRule(RuleFactory rf) {
            super(rf);
            this.rf = rf;
        }

        @Override
        public JClass apply(String nodeName, JsonNode node, JsonNode parent, JPackage jpackage, Schema schema) {
            // 1) Traverse normally so nested refs are visited
            JClass original = super.apply(nodeName, node, parent, jpackage, schema);

            // 2) We're only interested in the contentBlocks and messages nodes
            if (!"contentBlocks".equals(nodeName) && !"messages".equals(nodeName)) {
                return original;
            }

            // 2a) If items is a $ref to #/$defs/ContentBlock or #/$defs/Message, force-generate all branches
            JsonNode items = node.get("items");
            if (items != null) {
                JsonNode refNode = items.get("$ref");
                if (refNode != null && refNode.isTextual()) {
                    String ref = refNode.asText(); // expect "#/$defs/ContentBlock" or "#/$defs/Message"
                    if (ref.endsWith("/ContentBlock") || ref.endsWith("/Message")) {
                        // Find the union in the root schema
                        var refName = ref.substring(ref.lastIndexOf('/') + 1);
                        JsonNode root = CJSONAnyOfRuleFactory.rootOf(schema).getContent();
                        JsonNode defs = root.get("$defs");
                        if (defs != null) {
                            JsonNode cbDef = defs.get(refName);
                            if (cbDef != null) {
                                JsonNode union = cbDef.has("oneOf") ? cbDef.get("oneOf") : cbDef.get("anyOf");
                                if (union != null && union.isArray()) {
                                    for (JsonNode element : union) {
                                        JsonNode branchRef = element.get("$ref");
                                        if (branchRef != null && branchRef.isTextual()) {
                                            String branch = branchRef.asText(); // e.g. "#/$defs/TextBlock"
                                            String simple = CJSONAnyOfRuleFactory.simpleNameFromRef(branch);
                                            // Ask SchemaRule to generate the referenced element
                                            rf.getSchemaRule().apply(simple, element, parent, jpackage, schema);

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 2b) Force the property type to List<ContentBlock>
            GenerationConfig cfg = rf.getGenerationConfig();
            JCodeModel cm = jpackage.owner();
            JClass list = cm.ref(java.util.List.class);
            JClass cb;
            if ("contentBlocks".equals(nodeName)) {
                cb = ((CJSONAnyOfRuleFactory) rf).ensureContentBlockInterface(jpackage, cfg);
            } else {
                cb = ((CJSONAnyOfRuleFactory) rf).ensureMessageInterface(jpackage, cfg);
            }
            return list.narrow(cb);
        }
    }

    static class ConstToEnumPropertyRule extends PropertyRule {

        public ConstToEnumPropertyRule(RuleFactory ruleFactory) {
            super(ruleFactory);
        }

        @Override
        public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass jclass, Schema schema) {
            if (nodeName.equals("blockType") || nodeName.equals("messageType")) {
                var c = node.get("const");
                if (c == null) {
                    return super.apply(nodeName, node, parent, jclass, schema);
                }

                JsonNodeFactory nf = new JsonNodeFactory(false);
                var newNode = nf.objectNode()
                        .put("type", "string")
                        .put("default", c.textValue());
                return super.apply(nodeName, newNode, parent, jclass, schema);
            }
            return super.apply(nodeName, node, parent, jclass, schema);
        }
    }
}

