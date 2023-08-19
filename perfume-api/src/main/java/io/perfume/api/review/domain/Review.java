package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Review extends BaseTimeDomain {

  private final Long id;

  private final String feeling;

  private final String situation;

  private final STRENGTH strength;

  private final Long duration;

  private final SEASON season;

  private final Long perfumeId;

  private final Long userId;

  public Review(Long id, String feeling, String situation, STRENGTH strength, Long duration,
                SEASON season, Long perfumeId, Long userId, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.feeling = feeling;
    this.situation = situation;
    this.strength = strength;
    this.duration = duration;
    this.season = season;
    this.perfumeId = perfumeId;
    this.userId = userId;
  }

  public static Review create(String feeling, String situation, STRENGTH strength, Long duration,
                              SEASON season, Long perfumeId, Long userId, LocalDateTime now) {
    return new Review(null, feeling, situation, strength, duration, season, perfumeId, userId,
        now, now, null);
  }
}
