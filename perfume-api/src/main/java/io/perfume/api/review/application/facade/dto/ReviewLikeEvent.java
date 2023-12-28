package io.perfume.api.review.application.facade.dto;

public record ReviewLikeEvent(String content, Long receiveUserId) {
  private static final String EVENT_MESSAGE = "다른 사용자가 리뷰에 좋아요 표시를 했습니다.";

  public ReviewLikeEvent(Long receiveUserId) {
    this(EVENT_MESSAGE, receiveUserId);
  }
}
