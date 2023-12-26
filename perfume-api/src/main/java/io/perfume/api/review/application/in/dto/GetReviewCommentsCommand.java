package io.perfume.api.review.application.in.dto;

import dto.repository.CursorDirection;

public record GetReviewCommentsCommand(int size, String before, String after, Long reviewId) {

  public String getCursor() {
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
