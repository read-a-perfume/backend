package io.perfume.api.perfume.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Perfume extends BaseTimeDomain {

  private Long id;
  private String name;
  private String story;
  private Concentration concentration;
  private String perfumeShopUrl;
  private Long brandId;
  private Long categoryId;
  private Long thumbnailId;
  private List<Long> imageIds;
  private NotePyramidIds notePyramidIds;

  @Builder
  public Perfume(
      Long id,
      String name,
      String story,
      Concentration concentration,
      String perfumeShopUrl,
      Long brandId,
      Long thumbnailId,
      List<Long> imageIds,
      NotePyramidIds notePyramidIds,
      Long categoryId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.story = story;
    this.concentration = concentration;
    this.perfumeShopUrl = perfumeShopUrl;
    this.brandId = brandId;
    this.categoryId = categoryId;
    this.thumbnailId = thumbnailId;
    this.imageIds = imageIds;
    this.notePyramidIds = notePyramidIds;
  }
}
