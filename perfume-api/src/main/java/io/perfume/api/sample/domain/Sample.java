package io.perfume.api.sample.domain;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "sample")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Sample extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Builder
    public Sample(@NotNull Long id, @NotNull String name, @NotNull LocalDateTime createdAt, LocalDateTime deletedAt) {
        super(createdAt, deletedAt);
        this.name = name;
        this.id = id;
    }

    @NotNull
    public Long getId() {
        return id;
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
