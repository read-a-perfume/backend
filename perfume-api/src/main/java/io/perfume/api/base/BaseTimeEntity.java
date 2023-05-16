package io.perfume.api.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTimeEntity {

    @NotNull
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    protected BaseTimeEntity() {}

    protected BaseTimeEntity(@NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.deletedAt = null;
    }

    public BaseTimeEntity(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.deletedAt = deletedAt;
    }

    protected void makeDeletedAt(LocalDateTime now) {
        if (this.deletedAt != null) {
            return;
        }

        this.deletedAt = now;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
