package io.perfume.api.sample.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "sample")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Access(AccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sample {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column()
    private LocalDateTime deletedAt;

    @Builder
    public Sample(@NotNull String name, @NotNull LocalDateTime now) {
        this.name = name;
        this.createdAt = now;
        this.updatedAt = now;
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

    public void markDeleted(LocalDateTime now) throws IllegalAccessException {
        if (this.deletedAt != null) {
            throw new IllegalAccessException();
        }
        this.deletedAt = now;
    }
}
