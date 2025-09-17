package dev.cjson.schema.types.toolset;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record Toolset(

        @NotNull String id,

        @Schema(description = "The kind of toolset represented by this definition.")
        @NotNull ToolsetKind kind,

        String version,

        @Schema(description = """
                Optional server connection details for MCP/remote toolsets.
                
                Applications are encouraged, although not enforced, to support environment variable injection into the
                server values.
                
                As an example, a "token" header with a "${env:MODEL_TOKEN}" would inject the value of the environment
                variable "MODEL_TOKEN" into the "token" value.
                
                Applications MUST NOT store plain-text credentials here and should use references to secret stores
                or environment variable names.
                
                For secret referencing, we recommend a "${secret:SECRET_NAME}" that is similar to the "env" case, but
                is up to the application to decide where they fetch the secrets from.
                """)
        Map<String, Object> server,

        @Schema(description = """
                Additional headers to be included in the HTTP requests for "http" defined tools (mainly HTTP MCP servers).
                
                Applications are encouraged, although not enforced, to support environment variable injection into the
                header values.
                
                As an example, a "token" header with a "${env:MODEL_TOKEN}" would inject the value of the environment
                variable "MODEL_TOKEN" into the "token" header.
                
                Applications MUST NOT store plain-text credentials here and should use references to secret stores
                or environment variable names.
                
                For secret referencing, we recommend a "${secret:SECRET_NAME}" that is similar to the "env" case, but
                is up to the application to decide where they fetch the secrets from.
                """)
        Map<String, String> headers,

        @Schema(description = "Default flags that apply to all tools unless overridden at the tool level.")
        ToolsetDefaults toolsetDefaults,

        @Schema(description = """
                The tools that are exposed/defined by this toolset.
                
                It is the responsibility of the application to fetch the list of tools from the respective provider
                (e.g. MCP server) and to list them here.
                
                Part of the intention with this is to allow the user(s) to define the permissions of each tool individually.
                
                For example, for an MCP Server that provides "file system access tools", a user might want to allow all
                file reads inside a folder without requiring approval, but might want to require approval for any deletions.
                """)
        List<Tool> tools,

        @Schema(description = """
                Extensions can be used to add custom application-specific data to the toolset definition.
                
                Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
                """)
        Map<String, Object> extensions
) {

    public enum ToolsetKind {
        @JsonProperty("builtin") BUILTIN,
        @JsonProperty("mcp") MCP,
        @JsonProperty("uri") URI
    }

    @Schema(description = "Default flags that apply to all tools unless overridden at the tool level.")
    public record ToolsetDefaults(
            @Schema(description = "Whether tools are enabled by default.", defaultValue = "true")
            Boolean enabled,

            @Schema(description = "Whether tools require approval by default.", defaultValue = "false")
            Boolean requiresApproval
    ) {
    }

}