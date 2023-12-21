package io.perfume.api.notification.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundNotificationTypeException extends CustomHttpException {
  public NotFoundNotificationTypeException() {
    super(HttpStatus.NOT_FOUND, "존재하지 않는 유형의 알림입니다.", "존재하지 않는 유형의 알림입니다.", LogLevel.ERROR);
  }
}
