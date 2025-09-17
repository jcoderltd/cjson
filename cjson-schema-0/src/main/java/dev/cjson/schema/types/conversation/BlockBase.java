package dev.cjson.schema.types.conversation;

import jakarta.validation.constraints.NotNull;

public abstract class BlockBase {
    @NotNull
    public String id;
    @NotNull
    public String createdAt;
    public String updatedAt;

    protected BlockBase(String id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
