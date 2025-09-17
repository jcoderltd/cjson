package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record ToolApprovalBlock(
        @Schema(description = "The unique identifier of this tool approval block in the conversation. UUIDs/ULIDs are recommended but not enforced.")
        @NotNull String id,

        @Schema(description = "The unique identifier of the tool call this tool approval block is associated with.")
        @NotNull String toolCallId,

        @Schema(description = "The approval state provided by the user.")
        @NotNull ToolApprovalState toolApprovalState,

        @Schema(description = "The id of the user that approved the tool call.")
        String approvedBy,

        @Schema(description = "The optional reason the tool call was approved/rejected/canceled.")
        String reason,

        @NotNull OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) implements ContentBlock {

    public enum ToolApprovalState {
        @JsonProperty("approved") APPROVED,
        @JsonProperty("rejected") REJECTED,
        @JsonProperty("canceled") CANCELED
    }
}