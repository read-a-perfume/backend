package io.perfume.api.perfume.domain;

import io.perfume.api.base.BaseTimeDomain;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PerfumeFavorite extends BaseTimeDomain {

  private final Long userId;
  private final Long perfumeId;

  public PerfumeFavorite(Long userId, Long perfumeId,
                         LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.userId = userId;
    this.perfumeId = perfumeId;
  }
  
  public static PerfumeFavorite create(Long userId, Long perfumeId, LocalDateTime now) {
    return new PerfumeFavorite(userId, perfumeId,
        now, now, null);
  }
}
