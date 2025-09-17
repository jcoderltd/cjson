package dev.cjson.schema.types.toolset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@Schema(description = "A tool that is exposed/defined by this toolset.")
public record Tool(
        @Schema(description = """
                Unique identifier for this tool.
                
                This is the name that applications MUST send to the model as part of the tool definitions.
                """)
        @NotNull String name,

        @Schema(description = "A human-readable summary of this tool.")
        String summary,

        @Schema(description = """
                JSON Schema (or schema-like) describing args; free-form object.
                """)
        Map<String, Object> argsSchema,

        @Schema(description = "Whether this tool is enabled (overrides toolset.defaults.enabled).", defaultValue = "true")
        Boolean enabled,

        @Schema(description = "Whether this command requires approval (overrides toolset.defaults.requiresApproval).", defaultValue = "false")
        Boolean requiresApproval,

        @Schema(description = "Optional example payloads/usages.")
        List<Map<String, Object>> examples,

        @Schema(description = """
                Extensions can be used to add custom application-specific data to the tool definition.
                
                Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
                """)
        Map<String, Object> extensions
) {
}
