package io.perfume.api.review.adapter.out.persistence.repository.comment;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "review_comment",
    indexes = {
      @Index(name = "idx_review_comment_1", columnList = "reviewId"),
      @Index(name = "idx_review_comment_2", columnList = "userId")
    })
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ReviewCommentEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(nullable = false)
  private Long reviewId;

  @Column(nullable = false)
  private Long userId;

  private String content;

  @Builder
  public ReviewCommentEntity(
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
}
