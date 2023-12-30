package io.perfume.api.file.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class SaveFileNotFoundException extends CustomHttpException {

  public SaveFileNotFoundException(String message) {
    super(
        HttpStatus.BAD_REQUEST,
        "Failed to save file because " + message,
        "failed to save empty file.",
        LogLevel.INFO);
  }
}
