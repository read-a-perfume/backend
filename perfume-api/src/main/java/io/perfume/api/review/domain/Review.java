package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Review extends BaseTimeDomain {

  private final Long id;

  private final String feeling;

  private final String shortReview;

  private final Strength strength;

  private final Duration duration;

  private final DayType dayType;

  private final Long perfumeId;

  private final Season season;

  private final Long userId;

  public Review(Long id, String feeling, String shortReview, Strength strength, Duration duration,
                DayType dayType, Long perfumeId, Long userId, Season season,
                LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.feeling = feeling;
    this.shortReview = shortReview;
    this.strength = strength;
    this.duration = duration;
    this.dayType = dayType;
    this.perfumeId = perfumeId;
    this.userId = userId;
    this.season = season;
  }

  public static Review create(String feeling, String situation, Strength strength, Duration duration,
                              DayType dayType, Long perfumeId, Long userId, Season season,
                              LocalDateTime now) {
    return new Review(null, feeling, situation, strength, duration, dayType, perfumeId, userId,
        season,
        now, now, null);
  }

  public boolean isOwner(Long userId) {
    return !Objects.isNull(this.userId) && this.userId.equals(userId);
  }

  public boolean isMine(long userId) {
    return this.userId.equals(userId);
  }
}
