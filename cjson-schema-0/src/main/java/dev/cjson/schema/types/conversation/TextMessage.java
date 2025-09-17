package dev.cjson.schema.types.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@Schema(description = """
        A Message is the building unit inside a Conversation.
        
        TextMessages represent the most basic type of message in a conversation, represented by a block of text. They are
        equivalent to a CompositeMessage that contains a single TextBlock inside of it.
        """)
public record TextMessage(
        @Schema(description = "The unique identifier for this message inside the application. UUIDs/ULIDs are recommended but not enforced.")
        @NotNull String id,

        @Schema(description = """
                The role of the message in the conversation.
                
                Different providers handle this differently, however, there appears to be a recommended practice of consolidating
                all system/developer messages into a single initial message in the conversation.
                
                As such, for "system/developer" messages, see the `Conversation::systemMessage` field.
                """)
        @NotNull MessageRole role,

        @Schema(description = """
                The index of the message in the conversation.
                
                The index is optional as the order of the messages in the array can be understood/implied as the index.
                
                However, when a message in the conversation is "retried", for example, if the user wants a second take from the LLM,
                then the index will become an useful indicator that two or more messages are meant to be displayed on the same position
                in the conversation.
                """)
        Integer index,

        @Schema(description = """
                Whether the message is the preferred option to display when multiple messages share the same index in a conversation.
                
                In general, this field has no effect if the message doesn't have an index and doesn't share the same index with other messages.
                """)
        boolean isPreferred,

        @Schema(description = "Used to mark messages that are pinned by users as a sign of relevance. Useful for bookmarking.")
        boolean pinned,

        @Schema(description = "The id of the user that sent the message. This is specific to messages with \"user\" role.")
        String senderId,

        @Schema(description = """
                Optional metadata about the execution of the LLM/assistant model. Examples of what to add here: the model name, cost, number of input/output tokens, etc.
                
                A more typed version of this metadata will be more formally defined in future versions.
                """)
        Map<String, Object> assistantMetadata,

        @Schema(description = "The text content of the message.")
        String content,

        @Schema(description = """
                The list of attachments that are associated with the message.
                
                These are not treated as content blocks and SHOULD BE separate from content blocks. These SHOULD BE treated as additional context to
                a model.
                
                Hopefully, in a not so distant future, LLM APIs will accept parameters (like SQL Parameters) that help us prevent context injection
                (https://cheatsheetseries.owasp.org/cheatsheets/LLM_Prompt_Injection_Prevention_Cheat_Sheet.html).
                """)
        List<MessageAttachment> attachments,

        @Schema(description = """
                An optional audit trail that shows when the message was created or modified.
                
                The first entry in an audit trail SHOULD BE the CREATED operation.
                """)
        List<AuditEntry> auditTrail,

        @Schema(description = "Metadata that can be used to store any additional information about the message.")
        Map<String, Object> metadata,

        @Schema(description = """
                Extensions can be used to add custom application-specific data to the message.
                
                Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
                """)
        Map<String, Object> extensions

) implements Message {

}