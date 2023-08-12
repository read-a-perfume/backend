package io.perfume.api.perfume.adapter.out.persistence.perfume;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

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
    @Comment("PK")
    private Long id;

    @NotNull
    @Comment("향수 이름")
    private String name;

    @NotNull
    @Comment("향수 이야기")
    private String story;

    @Comment("농도 필드로 변경 예정")
    private String strength;

    @Comment("농도 필드로 변경 예정")
    private String duration;

    @Comment("향수 가격")
    private Long price;

    @Comment("향수 용량")
    private Long capacity;

    @Comment("Brand Table FK")
    @NotNull
    private Long brandId;

    @Comment("Thumbnail Table FK")
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
