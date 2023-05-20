package io.perfume.api.base;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseTimeDomain {

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    protected BaseTimeDomain(@NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void markDelete(LocalDateTime now) {
        this.deletedAt = now;
    }
}
