package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record MessageAttachment(
        @Schema(description = "The unique identifier for this attachment inside the conversation. UUIDs/ULIDs are recommended but not enforced.")
        @NotNull String id,

        @Schema(description = "The kind of attachment (file/image/audio/video/link/other).")
        @NotNull AttachmentKind attachmentKind,

        @Schema(description = """
                The name of the attachment. Unique names aren't required, although are recommended inside a message
                to avoid confusions.
                """)
        @NotNull
        String name,

        @Schema(description = "The URI of the attachment. SHOULD BE provided if base64content is not provided directly.")
        String uri,

        @Schema(description = "The base64 encoded content of the attachment. SHOULD BE provided if uri is not provided.")
        String base64content,

        @Schema(description = "The mime type of the attachment.")
        String mime,

        @Schema(description = "The SHA-256 hash of the attachment content.")
        String sha256,

        @Schema(description = "The size of the attachment in bytes.")
        Long sizeInBytes,

        @Schema(description = "Metadata that can be used to store any additional information about the attachment.")
        Map<String, Object> metadata
) {
    public enum AttachmentKind {
        @JsonProperty("file") FILE,
        @JsonProperty("image") IMAGE,
        @JsonProperty("audio") AUDIO,
        @JsonProperty("video") VIDEO,
        @JsonProperty("link") LINK,
        @JsonProperty("other") OTHER
    }
}
