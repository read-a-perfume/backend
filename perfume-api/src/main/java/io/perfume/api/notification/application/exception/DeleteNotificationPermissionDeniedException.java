package io.perfume.api.notification.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class DeleteNotificationPermissionDeniedException extends CustomHttpException {
  public DeleteNotificationPermissionDeniedException(Long userId, Long notificationId) {
    super(
        HttpStatus.FORBIDDEN,
        "알림을 삭제할 수 없습니다.",
        "잘못된 알림 삭제 요청 userId=" + userId + ", notificationId=" + notificationId,
        LogLevel.ERROR);
  }
}
