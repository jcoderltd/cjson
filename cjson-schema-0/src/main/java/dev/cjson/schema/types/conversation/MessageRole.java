package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageRole {
    @JsonProperty("user") USER,
    @JsonProperty("assistant") ASSISTANT,
    @JsonProperty("tool") TOOL
}
