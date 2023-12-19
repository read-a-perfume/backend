package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class UserNotAuthenticatedException extends CustomHttpException {
  public UserNotAuthenticatedException() {
    super(HttpStatus.UNAUTHORIZED, "User Not Authenticated. Please login.", "", LogLevel.INFO);
  }
}
