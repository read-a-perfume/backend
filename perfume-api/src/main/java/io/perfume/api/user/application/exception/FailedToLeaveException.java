package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedToLeaveException extends CustomHttpException {
  public FailedToLeaveException(String message, String logMessage) {
    super(HttpStatus.UNAUTHORIZED, message, logMessage, LogLevel.WARN);
  }
}
