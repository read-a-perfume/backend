package io.perfume.api.review.application.out.comment.dto;

public record ReviewCommentCount(long reviewId, long count) {

  public static final ReviewCommentCount ZERO = new ReviewCommentCount(0, 0);
}
