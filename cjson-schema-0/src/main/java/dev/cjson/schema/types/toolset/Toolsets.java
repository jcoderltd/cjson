package dev.cjson.schema.types.toolset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(title = "Toolsets")
public record Toolsets(

        @Schema(defaultValue = "https://cjson.dev/schema/0.1.0/toolsets/cjson-toolsets.0.1.0.json")
        @NotNull String schema,

        @Schema(defaultValue = "application/vnd.cjson-toolsets+json")
        String mediaType,

        List<Toolset> toolsets

) {
}
