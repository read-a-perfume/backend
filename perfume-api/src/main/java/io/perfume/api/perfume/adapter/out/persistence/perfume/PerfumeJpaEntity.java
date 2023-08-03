package io.perfume.api.perfume.adapter.out.persistence.perfume;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "perfume")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class PerfumeJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String story;

    private String strength;

    private String duration;

    private Long price;

    private Long capacity;

    @NotNull
    private Long brandId;

    private Long thumbnailId;

    public PerfumeJpaEntity(
            Long id, String name, String story, String strength, String duration, Long price, Long capacity,
            Long brandId, Long thumbnailId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.story = story;
        this.strength = strength;
        this.price = price;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.capacity = capacity;
        this.brandId = brandId;
        this.thumbnailId = thumbnailId;
    }
}
