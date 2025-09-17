package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "blockType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextBlock.class, name = "text"),
        @JsonSubTypes.Type(value = ToolCallBlock.class, name = "toolCall"),
        @JsonSubTypes.Type(value = ToolApprovalBlock.class, name = "toolApproval"),
        @JsonSubTypes.Type(value = ToolResultBlock.class, name = "toolResult"),
        @JsonSubTypes.Type(value = ThinkingBlock.class, name = "thinking")
})
public sealed interface ContentBlock
        permits TextBlock, ThinkingBlock, ToolApprovalBlock, ToolCallBlock, ToolResultBlock {

    @NotNull
    String id();

    @NotNull
    OffsetDateTime createdAt();

    OffsetDateTime updatedAt();
}
