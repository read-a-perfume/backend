package io.perfume.api.perfume.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PerfumeTheme extends BaseTimeDomain {
  private Long id;
  private String title;
  private String content;
  private Long thumbnailId;
  private List<Long> perfumeIds;

  @Builder
  public PerfumeTheme(
      String title,
      String content,
      Long thumbnailId,
      List<Long> perfumeIds,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.title = title;
    this.content = content;
    this.thumbnailId = thumbnailId;
    this.perfumeIds = perfumeIds;
  }
}
