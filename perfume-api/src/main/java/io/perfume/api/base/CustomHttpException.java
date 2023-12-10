package io.perfume.api.base;

import org.springframework.http.HttpStatus;

public class CustomHttpException extends RuntimeException {

  private final HttpStatus httpStatus;

  private final LogLevel logLevel;

  private final String logMessage;

  public CustomHttpException(
      HttpStatus httpStatus, String message, String logMessage, LogLevel logLevel) {
    super(message);
    this.httpStatus = httpStatus;
    this.logLevel = logLevel;
    this.logMessage = logMessage;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public LogLevel getLogLevel() {
    return logLevel;
  }

  public String getLogMessage() {
    return logMessage;
  }
}
