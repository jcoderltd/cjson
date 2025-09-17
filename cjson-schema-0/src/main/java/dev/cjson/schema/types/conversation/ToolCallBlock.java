package dev.cjson.schema.types.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Map;

@Schema(description = """
        A "Tool Call" block represents a request by the LLM to execute a tool (e.g. provided via an MCP or via the application).
        
        Tool calls are supported by some models and different applications can add "user-mediated tool execution" to their flows
        to reduce the risk of data loss or other unwanted consequences. If user mediation is required, you can make use of the
        ToolApprovalBlock to represent whether a tool call was approved or not inside a flow.
        
        If a tool call doesn't require approval or if it was approved by a user, then the result of such tool execution can be
        represented as a ToolResultBlock.
        """)
public record ToolCallBlock(
        @Schema(description = "The unique identifier of this tool approval block in the conversation. UUIDs/ULIDs are recommended but not enforced.")
        @NotNull String id,

        @NotNull OffsetDateTime createdAt,
        OffsetDateTime updatedAt,

        @Schema(description = "The tool that the LLM is requesting to be executed")
        @NotNull ToolRef toolRef,

        @Schema(description = "The arguments that the LLM is requesting to be passed to the tool")
        Map<String, Object> args,

        @Schema(description = """
                Indicates if the tool call requires user-approval before execution.
                
                This is driven by the tool configuration in the application and by any tool overrides specific to this
                conversation.
                """)
        boolean requiresApproval
) implements ContentBlock {

}