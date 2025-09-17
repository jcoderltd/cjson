package dev.cjson.schema.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;
import com.github.victools.jsonschema.module.swagger2.Swagger2Module;
import dev.cjson.schema.types.Version;
import dev.cjson.schema.types.conversation.Conversation;
import dev.cjson.schema.types.model.Models;
import dev.cjson.schema.types.toolset.Toolsets;

import java.nio.file.Files;
import java.nio.file.Path;

public class CJSONSchemaGenerator {

    public static void main(String[] args) throws Exception {
        var cfg = new SchemaGeneratorConfigBuilder(
                SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                .with(Option.FLATTENED_ENUMS, Option.DEFINITIONS_FOR_MEMBER_SUPERTYPES)
                .with(new JacksonModule(
                        JacksonOption.RESPECT_JSONPROPERTY_REQUIRED,
                        JacksonOption.FLATTENED_ENUMS_FROM_JSONPROPERTY,
                        JacksonOption.ALWAYS_REF_SUBTYPES
                ))
                .with(new JakartaValidationModule(
                        JakartaValidationOption.NOT_NULLABLE_FIELD_IS_REQUIRED,
                        JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS
                ))
                .with(new Swagger2Module())
                .with(new CJSONModule());

        SchemaGeneratorConfig config = cfg.build();
        var generator = new SchemaGenerator(config);
        var mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        String schemaPrefix = "../cjson-schema-0/schemas/" + Version.VERSION;
        String schemaExt = Version.VERSION + ".json";
        write(generator, mapper, Toolsets.class, schemaPrefix + "/toolsets/cjson-toolsets-" + schemaExt);
        write(generator, mapper, Models.class, schemaPrefix + "/models/cjson-models-" + schemaExt);
        write(generator, mapper, Conversation.class, schemaPrefix + "/conversation/cjson-" + schemaExt);
    }

    static void write(SchemaGenerator g, ObjectMapper m, Class<?> type, String out) throws Exception {
        var jsonNode = g.generateSchema(type);
        var json = m.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        var outPath = Path.of(out);
        Files.createDirectories(outPath.getParent());
        Files.writeString(outPath, json);
        System.out.println("Wrote " + out);
    }

}
