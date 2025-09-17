package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Map;

public record ToolResultBlock(
        @Schema(description = "The unique identifier of this tool result block in the conversation. UUIDs/ULIDs are recommended but not enforced.")
        @NotNull String id,
        @NotNull OffsetDateTime createdAt,
        OffsetDateTime updatedAt,

        @Schema(description = "The id of the tool call this tool result block is associated with.")
        @NotNull String toolCallId,

        @Schema(description = "The result of the tool execution")
        @NotNull ToolResultState toolResultState,
        Number durationMs,

        @Schema(description = """
                The output of the tool execution.
                
                Applications SHOULD use this output feeding the tool execution result into the models.
                """)
        Object output,

        @Schema(description = """
            Metadata that can be used to store any additional information about the execution of the tool.
            """)
        Map<String, Object> metadata,

        ToolResultErrorInfo toolResultError
) implements ContentBlock {

    public enum ToolResultState {
        @JsonProperty("succeeded") SUCCEEDED,
        @JsonProperty("failed") FAILED,
        @JsonProperty("timed_out") TIMED_OUT,
        @JsonProperty("canceled") CANCELED
    }

    public record ToolResultErrorInfo(String code, String message, Object data) {
    }
}