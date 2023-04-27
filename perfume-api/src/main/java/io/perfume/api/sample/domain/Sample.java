package io.perfume.api.sample.domain;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "sample")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Sample extends BaseTimeEntity {

    @NotNull
    @Column(nullable = false)
    private String name;

    @Builder
    public Sample(@NotNull Long id, @NotNull String name, @NotNull LocalDateTime createdAt, LocalDateTime deletedAt) {
        super(id, createdAt, deletedAt);
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void changeName(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public void delete(LocalDateTime now) {
        this.makeDeletedAt(now);
    }
}
