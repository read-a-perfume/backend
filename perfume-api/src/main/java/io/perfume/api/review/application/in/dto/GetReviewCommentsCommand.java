package io.perfume.api.review.application.in.dto;

import dto.repository.CursorDirection;

public record GetReviewCommentsCommand(
    long size,
    Long before,
    Long after,
    Long reviewId
) {

  public Long getCursor() {
    if (before != null) {
      return before;
    }

    return after;
  }

  public boolean hasNext() {
    return after != null;
  }

  public boolean hasPrevious() {
    return before != null;
  }

  public CursorDirection getDirection() {
    if (before != null) {
      return CursorDirection.PREVIOUS;
    }

    return CursorDirection.NEXT;
  }
}
