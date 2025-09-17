package dev.cjson.schema.types.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@Schema(title = "Conversation", description = """
        A conversation between an actor and a model, typically a User <-> LLM conversation.
        
        The list of messages in the conversation should represent the "Conversation History" and it is not intended to represent the "Conversation Memory" for an LLM.
        In this sense, think of the specification as a standard way to store "what the user sees" from a Conversation and think about what will help with the UI/UX for
        your particular application.
        
        Many aspects of the specification are optional and can be used/discarded depending on the needs/requirements of each application. For example, audit trails,
        although recommended, are not mandatory in the specification.
        
        It is NOT the intention of this specification to be a "Memory Format" representation.
        
        Conversation data for "internal-usage" inside an application can, and SHOULD BE, different from the data used for export purposes.
        
        For example, the ownerId can be an actual identifier inside an application, but during export, it can be the user email, a secure one-way hash of the user identifier,
        or any other identifier that makes sense during the export process. Another example is the "metadata" field, where it might not make sense to export "application-specific"
        metadata that only makes sense to that specific application.
        
        Conversation data that is essential to the conversation, and in general "user owned", SHOULD BE exported as verbatim as possible, for example, the conversation title and the
        message contents.
        """)
public record Conversation(
        @Schema(defaultValue = "https://cjson.dev/schema/0.1.0/conversation/cjson-0.1.0.json")
        @NotNull String schemaUrl,

        @Schema(defaultValue = "application/vnd.cjson+json")
        String mediaType,

        @Schema(description = "The unique identifier for this conversation inside the application. UUIDs/ULIDs are recommended but not enforced.")
        @NotNull String id,

        ContentBlock tb,

        @Schema(description = """
                The title of the conversation. This is not required and can be left blank.
                
                Many applications tend to auto-generate a suggested title based on the first user message, however this is not required.
                """)
        String conversationTitle,

        @Schema(description = "The list of messages in this conversation. It can be null or empty to represent an empty conversation.")
        List<Message> messages,

        @Schema(description = "The id of the user that owns this conversation. Usually, but not necessarily, the user that created the conversation.")
        String ownerId,

        @Schema(description = "The id of the parent conversation. This is null if the conversation is a top-level conversation.")
        String parentId,

        @Schema(description = """
                The id of the model that this conversation is based on.
                
                When a user message is sent or when a tool execution response needs processing, this is the model that will be used for processing.
                
                This is null if the conversation doesn't have a model selected.
                """)
        String modelId,

        @Schema(description = """
                This represents the initial "system"/"developer" message in a conversation.
                
                Different providers handle the system/developer message differently. Applications MUST include this system message according to
                the expectations of the model being invoked if the application supports given model.
                
                Refs/examples:
                * https://platform.openai.com/docs/guides/text?prompt-templates-examples=simple#message-roles-and-instruction-following
                * https://docs.anthropic.com/en/api/messages
                """)
        String systemMessage,

        ToolOverride toolOverrides,

        @Schema(description = "An optional audit trail that shows when the conversation was created or modified.")
        List<AuditEntry> auditTrail,

        @Schema(description = """
                Whether the conversation is marked as private or not.
                
                In general, conversations that are marked as `isPrivate = true` should only be accessibly by the user identified by `ownerId`.
                
                When exporting conversations, the user SHOULD BE prompted to confirm if private conversations should also be exported.
                No private conversations should be exported without the user's consent.
                """)
        boolean isPrivate,

        @Schema(description = "Metadata that can be used to store any additional information about the conversation.")
        Map<String, Object> metadata,

        @Schema(description = """
                Extensions can be used to add custom application-specific data to the conversation.
                
                Extensions can be later adopted as first-class citizens of the CJSON model if they are general enough and cover a common use-case.
                """)
        Map<String, Object> extensions
) {

    public record ToolOverride(
            @Schema(description = "The id of the tool where the config is overridden.")
            @NotNull String toolId,

            @Schema(description = """
                    Indicates if the tool is enabled/disabled in this conversation.
                    
                    Disabled tools should NOT be made available to the LLM models. A null value means that the application default
                    for this tool applies. Each application is free to decide how defaults are applied.
                    
                    Applications are encouraged to make these default values transparent to the users.
                    """)
            Boolean enabled,

            @Schema(description = """
                    Indicates if the tool requires approval before being executed.
                    
                    A null value means that the application default for this tool applies. Each application is free to decide how defaults are applied.
                    """)
            Boolean requiresApproval,

            @Schema(description = """
                    The configuration overrides for this tool that are application specific.
                    
                    For example, an application might offer a "web content fetching" tool that doesn't require approval for a
                    user-provided whitelist of domain names via a 'app.fetch.whitelisted' configuration value.
                    """)
            Map<String, Object> configOverrides
    ) {
    }
}
