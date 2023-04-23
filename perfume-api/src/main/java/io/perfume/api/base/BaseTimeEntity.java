package io.perfume.api.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity extends BaseEntity {

    @NotNull
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column()
    private LocalDateTime deletedAt;

    protected BaseTimeEntity() {}

    protected BaseTimeEntity(@NotNull Long id, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.deletedAt = null;
    }

    protected void makeDeletedAt(LocalDateTime now) {
        if (this.deletedAt != null) {
            return;
        }

        this.deletedAt = now;
    }
}
