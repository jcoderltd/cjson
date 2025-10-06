package dev.cjson.schema.types.model;

import dev.cjson.schema.types.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(title = "Models", description = """
        A list of model definitions defined/enabled by the user.
        """)
public record Models(
        @Schema(defaultValue = "https://schema.cjson.dev/0/models/cjson-models-" + Version.VERSION + ".schema.json")
        @NotNull String schemaUrl,

        @Schema(defaultValue = "application/vnd.cjson-models+json")
        String mediaType,

        List<ModelDefinition> modelDefinitions
) {
}
