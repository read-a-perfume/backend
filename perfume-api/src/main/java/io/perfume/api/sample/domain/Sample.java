package io.perfume.api.sample.domain;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(name = "sample")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sample extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public Sample(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
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
