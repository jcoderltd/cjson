package dev.cjson.schema.types.toolset;

import dev.cjson.schema.types.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(title = "Toolsets")
public record Toolsets(

        @Schema(defaultValue = "https://schema.cjson.dev/0/toolsets/cjson-toolsets-" + Version.VERSION + ".schema.json")
        @NotNull String schema,

        @Schema(defaultValue = "application/vnd.cjson-toolsets+json")
        String mediaType,

        List<Toolset> toolsets

) {
}
