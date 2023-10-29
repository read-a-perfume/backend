package io.perfume.api.perfume.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PerfumeFavorite extends BaseTimeDomain {

  private Long id;
  private Long userId;
  private Long perfumeId;

  public PerfumeFavorite(Long id, Long userId, Long perfumeId,
      LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.userId = userId;
    this.perfumeId = perfumeId;
  }
  
  public static PerfumeFavorite create(Long userId, Long perfumeId, LocalDateTime now) {
    return new PerfumeFavorite(null, userId, perfumeId,
        now, now, null);
  }
}
