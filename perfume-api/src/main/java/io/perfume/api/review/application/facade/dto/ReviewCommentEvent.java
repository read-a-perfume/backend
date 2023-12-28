package io.perfume.api.review.application.facade.dto;

public record ReviewCommentEvent(String content, Long receiveUserId) {

  private static final String EVENT_MESSAGE = "새로운 댓글이 달렸습니다.";

  public ReviewCommentEvent(Long receiveUserId) {
    this(EVENT_MESSAGE, receiveUserId);
  }
}
