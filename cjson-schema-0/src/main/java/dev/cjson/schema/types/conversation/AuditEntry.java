package dev.cjson.schema.types.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record AuditEntry(

        @NotNull
        @Schema(description = """
            The type of action performed on the resource. Deleted and Restored operations are only available in systems that perform
            soft-deletes of the data or that provide a grace period before an entry is permanently deleted.
            """)
        Action action,

        @NotNull
        @Schema(description = "The id of the actor in the system who performed the action")
        String actorId,

        @NotNull
        @Schema(description = "The timestamp of the action")
        OffsetDateTime timestamp,

        @Schema(description = "A description of the change", example = "Updated the conversation title")
        String changeDescription
) {

    public enum Action {
        @JsonProperty("created") CREATED,
        @JsonProperty("updated") UPDATED,
        @JsonProperty("deleted") DELETED,
        @JsonProperty("restored") RESTORED
    }

}
