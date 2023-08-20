package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class UserPasswordNotMatchException extends CustomHttpException {
  public UserPasswordNotMatchException(Long userId) {
    super(HttpStatus.UNAUTHORIZED, "user password not match from storage",
        "user password not match from storage. UserId: " + userId, LogLevel.WARN);
  }
}
