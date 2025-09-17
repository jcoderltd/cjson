package dev.cjson.codegen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.codemodel.*;
import org.apache.commons.lang.StringUtils;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.JsonPointerUtils;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.rules.*;
import org.jsonschema2pojo.util.ParcelableHelper;

import java.util.List;

public class ContentBlockRuleFactory extends RuleFactory {

    @Override
    public Rule<JPackage, JClass> getArrayRule() {
        return new ContentBlockArrayRule(this);
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

    static final List<String> CONTENT_BLOCK_NAMES = List.of("TextBlock", "ThinkingBlock", "ToolApprovalBlock", "ToolCallBlock", "ToolResultBlock");

    static boolean isBlockLike(String simpleName) {
        return CONTENT_BLOCK_NAMES.contains(simpleName);
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
                if (ContentBlockRuleFactory.isBlockLike(cls.name())) {
                    JClass cb = ((ContentBlockRuleFactory) rf)
                            .ensureContentBlockInterface(jpackage, rf.getGenerationConfig());
                    cls._implements(cb);
                }
            }
            return jt;
        }
    }

    /**
     * Generates branch classes and types contentBlocks as List<ContentBlock>.
     */
    static class ContentBlockArrayRule extends ArrayRule {
        private final RuleFactory rf;

        ContentBlockArrayRule(RuleFactory rf) {
            super(rf);
            this.rf = rf;
        }

        @Override
        public JClass apply(String nodeName, JsonNode node, JsonNode parent, JPackage jpackage, Schema schema) {
            // 1) Traverse normally so nested refs are visited
            JClass original = super.apply(nodeName, node, parent, jpackage, schema);

            // 2) We're only interested in the contentBlocks node
            if (!"contentBlocks".equals(nodeName)) {
                return original;
            }

            // 2a) If items is a $ref to #/$defs/ContentBlock, force-generate all branches
            JsonNode items = node.get("items");
            if (items != null) {
                JsonNode refNode = items.get("$ref");
                if (refNode != null && refNode.isTextual()) {
                    String ref = refNode.asText(); // expect "#/$defs/ContentBlock"
                    if (ref.endsWith("/ContentBlock")) {
                        // Find the union in the root schema
                        JsonNode root = ContentBlockRuleFactory.rootOf(schema).getContent();
                        JsonNode defs = root.get("$defs");
                        if (defs != null) {
                            JsonNode cbDef = defs.get("ContentBlock");
                            if (cbDef != null) {
                                JsonNode union = cbDef.has("oneOf") ? cbDef.get("oneOf") : cbDef.get("anyOf");
                                if (union != null && union.isArray()) {
                                    for (JsonNode element : union) {
                                        JsonNode branchRef = element.get("$ref");
                                        if (branchRef != null && branchRef.isTextual()) {
                                            String branch = branchRef.asText(); // e.g. "#/$defs/TextBlock"
                                            String simple = ContentBlockRuleFactory.simpleNameFromRef(branch);
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
            JClass cb = ((ContentBlockRuleFactory) rf).ensureContentBlockInterface(jpackage, cfg);
            return list.narrow(cb);
        }
    }

    static class ConstToEnumPropertyRule extends PropertyRule {

        public ConstToEnumPropertyRule(RuleFactory ruleFactory) {
            super(ruleFactory);
        }

        @Override
        public JDefinedClass apply(String nodeName, JsonNode node, JsonNode parent, JDefinedClass jclass, Schema schema) {
            if (nodeName.equals("blockType")) {
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

