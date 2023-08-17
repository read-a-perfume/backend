package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import java.time.LocalDateTime;
import java.util.List;
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

  private final List<Tag> tags;

  public Review(Long id, String feeling, String situation, STRENGTH strength, Long duration,
                SEASON season, Long perfumeId, Long userId, List<Tag> tags, LocalDateTime createdAt,
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
    this.tags = tags;
  }

  public static Review create(String feeling, String situation, STRENGTH strength, Long duration,
                              SEASON season, Long perfumeId, Long userId, List<Tag> tags,
                              LocalDateTime now) {
    return new Review(null, feeling, situation, strength, duration, season, perfumeId, userId, tags,
        now, now, null);
  }
}
