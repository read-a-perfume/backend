package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class AddMyTypeException extends CustomHttpException {
  public AddMyTypeException(String message) {
    super(HttpStatus.BAD_REQUEST, message, message, LogLevel.INFO);
  }
}
