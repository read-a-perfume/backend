package io.perfume.api.brand.domain;

import io.perfume.api.base.BaseTimeDomain;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Brand extends BaseTimeDomain {
  private Long id;
  private String name;
  private String story;
  private Long thumbnailId;

  @Builder
  public Brand(@NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt,
               LocalDateTime deletedAt, Long id, String name, String story, Long thumbnailId) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.story = story;
    this.thumbnailId = thumbnailId;
  }
}
