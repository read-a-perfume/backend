package io.perfume.api.notification.domain;

public enum NotificationType {
  REVIEW(""),
  COMMENT("리뷰/댓글")
  ;

  private final String type;

  NotificationType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
