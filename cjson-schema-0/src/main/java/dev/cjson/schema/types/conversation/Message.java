package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "messageType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompositeMessage.class, name = "composite"),
        @JsonSubTypes.Type(value = TextMessage.class, name = "text"),
})
public interface Message {
    @NotNull
    String id();

    @NotNull
    MessageRole role();

    Integer index();

    boolean isPreferred();

    boolean pinned();

    String senderId();

    Map<String, Object> assistantMetadata();

    List<MessageAttachment> attachments();

    List<AuditEntry> auditTrail();

    Map<String, Object> metadata();

    Map<String, Object> extensions();
}
