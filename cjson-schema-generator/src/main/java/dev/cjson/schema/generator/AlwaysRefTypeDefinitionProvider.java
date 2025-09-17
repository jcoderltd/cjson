package dev.cjson.schema.generator;

import com.fasterxml.classmate.ResolvedType;
import com.github.victools.jsonschema.generator.CustomDefinition;
import com.github.victools.jsonschema.generator.CustomDefinitionProviderV2;
import com.github.victools.jsonschema.generator.SchemaGenerationContext;
import dev.cjson.schema.types.conversation.Message;

public class AlwaysRefTypeDefinitionProvider implements CustomDefinitionProviderV2 {

    @Override
    public CustomDefinition provideCustomSchemaDefinition(ResolvedType javaType, SchemaGenerationContext context) {
        if (javaType.getErasedType().equals(Message.class)) {
            return new CustomDefinition(
                    context.createStandardDefinition(javaType, this),
                    CustomDefinition.DefinitionType.ALWAYS_REF, CustomDefinition.AttributeInclusion.YES
            );
        }
        return null;
    }
}
