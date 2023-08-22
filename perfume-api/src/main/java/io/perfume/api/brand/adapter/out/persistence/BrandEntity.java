package io.perfume.api.brand.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import lombok.*;

@Entity(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class BrandEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String story;

  private Long thumbnailId;

  @Builder
  public BrandEntity(Long id, String name, String story, Long thumbnailId,
                     LocalDateTime createdAt, LocalDateTime updatedAt,
                     LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.story = story;
    this.thumbnailId = thumbnailId;
  }
}
