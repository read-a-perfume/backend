package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class ReviewComment extends BaseTimeDomain {

  private final Long id;

  private final Long reviewId;

  private final Long userId;

  private String content;

  @Builder
  public ReviewComment(
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt,
      Long id,
      Long reviewId,
      Long userId,
      String content) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.reviewId = reviewId;
    this.userId = userId;
    this.content = content;
  }

  public static ReviewComment create(
      final Long reviewId, final Long userId, final String content, final LocalDateTime now) {
    Assert.notNull(reviewId, "reviewId must not be null");
    Assert.notNull(userId, "userId must not be null");
    Assert.hasText(content, "content must not be empty");

    return ReviewComment.builder()
        .reviewId(reviewId)
        .userId(userId)
        .content(content)
        .createdAt(now)
        .updatedAt(now)
        .deletedAt(null)
        .build();
  }

  public void markDelete(final LocalDateTime now) {
    this.markDelete(now);
  }

  public boolean isOwned(final long userId) {
    return this.userId == userId;
  }

  public void updateComment(final String newComment) {
    Assert.hasText(newComment, "newComment must not be empty");

    this.content = newComment;
  }
}
