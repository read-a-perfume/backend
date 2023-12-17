package io.perfume.api.notification.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Notification extends BaseTimeDomain {

  private final Long id;

  private final String content;

  private final String redirectUrl;

  private final Long receiveUserId;

  private final NotificationType notificationType;

  private Boolean isRead;

  public Notification(
      Long id,
      String content,
      String redirectUrl,
      Long receiveUserId,
      NotificationType notificationType,
      Boolean isRead,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.content = content;
    this.redirectUrl = redirectUrl;
    this.receiveUserId = receiveUserId;
    this.notificationType = notificationType;
    this.isRead = isRead;
  }

  public static Notification create(
      String content,
      String redirectUrl,
      Long receiveUserId,
      NotificationType notificationType,
      LocalDateTime now) {
    return new Notification(
        null, content, redirectUrl, receiveUserId, notificationType, false, now, now, null);
  }

  public boolean isOwner(Long userId) {
    return !Objects.isNull(this.receiveUserId) && this.receiveUserId.equals(userId);
  }

  public void readNotification() {
    this.isRead = true;
  }
}
