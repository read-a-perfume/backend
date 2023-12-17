package io.perfume.api.notification.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundNotificationException extends CustomHttpException {
  public NotFoundNotificationException(long notificationId) {
    super(
        HttpStatus.NOT_FOUND,
        "존재하지 않는 알림 정보입니다.",
        "존재하지 않는 알림 삭제 요청" + notificationId,
        LogLevel.ERROR);
  }
}
