package io.perfume.api.notification.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class ReadNotificationPermissionDeniedException extends CustomHttpException {
  public ReadNotificationPermissionDeniedException(Long userId, Long notificationId) {
    super(
        HttpStatus.FORBIDDEN,
        "알림을 확인할 수 없습니다.",
        "잘못된 알림 확인 요청 userId=" + userId + ", notificationId=" + notificationId,
        LogLevel.ERROR);
  }
}
