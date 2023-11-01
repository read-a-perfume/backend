package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Strength;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Review extends BaseTimeDomain {

  private final Long id;

  private final String feeling;

  private final String situation;

  private final Strength strength;

  private final Long duration;

  private final DayType dayType;

  private final Long perfumeId;


  private final Long userId;

  public Review(Long id, String feeling, String situation, Strength strength, Long duration,
                DayType dayType, Long perfumeId, Long userId, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.feeling = feeling;
    this.situation = situation;
    this.strength = strength;
    this.duration = duration;
    this.dayType = dayType;
    this.perfumeId = perfumeId;
    this.userId = userId;
  }

  public static Review create(String feeling, String situation, Strength strength, Long duration,
                              DayType dayType, Long perfumeId, Long userId, LocalDateTime now) {
    return new Review(null, feeling, situation, strength, duration, dayType, perfumeId, userId,
        now, now, null);
  }

  public boolean isOwner(Long userId) {
    return !Objects.isNull(this.userId) && this.userId.equals(userId);
  }
}
