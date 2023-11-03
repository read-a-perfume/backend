package io.perfume.api.mypage.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Follow extends BaseTimeDomain {

  private Long id;

  private Long followerId;

  private Long followingId;

  public Follow(Long id, Long followerId, Long followingId,
      LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.followerId = followerId;
    this.followingId = followingId;
  }

  public static Follow create(Long followerId, Long followingId, LocalDateTime now) {
    return new Follow(null, followerId, followingId, now, now, null);
  }
}

