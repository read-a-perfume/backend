package io.perfume.api.brand.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Brand extends BaseTimeDomain {
  private Long id;
  private String name;
  private String story;
  private String brandUrl;
  private Long thumbnailId;

  @Builder
  public Brand(
      Long id,
      String name,
      String story,
      String brandUrl,
      Long thumbnailId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.story = story;
    this.brandUrl = brandUrl;
    this.thumbnailId = thumbnailId;
  }
}
