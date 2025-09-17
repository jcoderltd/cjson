package dev.cjson.schema.types.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ToolRef(
        @Schema(description = "The name of the tool to be called.")
        @NotNull String name,
        String version,

        @Schema(description = """
                The id of the toolset that defines the provided tool name.
                
                Although optional, it is recommended to ensure correct traceability from the tool that was requested
                to the toolset that provides it. A "toolset" can be locally defined tools by the application, or tools
                provided by an MCP server.
                
                Note: Toolsets are defined in a separate `cjson-toolset` schema.
                """)
        String toolsetId
) {
}
