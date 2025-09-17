package dev.cjson.schema.generator;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.CustomDefinition;
import com.github.victools.jsonschema.generator.CustomDefinitionProviderV2;
import com.github.victools.jsonschema.generator.Module;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;

import java.util.Map;

public class CJSONModule implements Module {
    @Override
    public void applyToConfigBuilder(SchemaGeneratorConfigBuilder builder) {
        builder.forTypesInGeneral().withCustomDefinitionProvider(getCustomDefinitionForMap());
        builder.forTypesInGeneral().withCustomDefinitionProvider(new AlwaysRefTypeDefinitionProvider());
    }

    private static CustomDefinitionProviderV2 getCustomDefinitionForMap() {
        return (javaType, context) -> {
            if (!Map.class.isAssignableFrom(javaType.getErasedType())) {
                return null;
            }

            var keyType = javaType.getTypeParameters().get(0);
            var valueType = javaType.getTypeParameters().get(1);

            if (keyType.getErasedType().equals(String.class) &&
                    valueType.getErasedType().equals(Object.class)) {
                ObjectNode node = context.getGeneratorConfig().createObjectNode();
                node.put("type", "object");
                node.put("additionalProperties", true);
                node.put("existingJavaType", "java.util.Map<java.lang.String, java.lang.Object>");
                return new CustomDefinition(node, true);
            }

            if (keyType.getErasedType().equals(String.class) &&
                    valueType.getErasedType().equals(String.class)) {
                ObjectNode node = context.getGeneratorConfig().createObjectNode();
                node.put("type", "object");
                node.put("additionalProperties", true);
                node.put("existingJavaType", "java.util.Map<java.lang.String, java.lang.String>");
                return new CustomDefinition(node, true);
            }
            return null;
        };
    }
}
