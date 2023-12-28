package io.perfume.api.notification.domain;

public enum NotificationType {
  REVIEW(""),
  COMMENT("리뷰/댓글"),
  REVIEW_LIKE("리뷰/좋아요");

  private final String type;

  NotificationType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
