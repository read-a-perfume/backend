package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class UserConflictException extends CustomHttpException {
  public UserConflictException(String username, String email) {
    super(HttpStatus.CONFLICT, "동일한 username 또는 email이 존재합니다.", "signup failed by conflict of username(" + username + ") or email(" + email + ")",
        LogLevel.WARN);
  }
}
