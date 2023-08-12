package io.perfume.api.brand.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class BrandJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Comment("PK")
    private Long id;

    @NotBlank
    @Comment("브랜드 이름")
    private String name;

    @NotBlank
    @Comment("브랜드 이야기")
    private String story;

    @Comment("Thumbnail Table FK")
    private Long thumbnailId;

    public BrandJpaEntity(Long id, String name, String story, Long thumbnailId, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt, @NotNull LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.story = story;
        this.thumbnailId = thumbnailId;
    }
}
