package io.perfume.api.common.advice;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(CustomHttpException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomHttpException exception) {
    printLog(exception.getMessage(), exception.getLogLevel());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(exception.getHttpStatus().getReasonPhrase())
        .statusCode(exception.getHttpStatus().value())
        .message(exception.getMessage())
        .build();

    return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ErrorResponse> handleMethodArgumentValidException(MethodArgumentNotValidException exception) {
    BindingResult bindingResult = exception.getBindingResult();
    StringBuilder stringBuilder = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      stringBuilder.append(fieldError.getField()).append(": ");
      stringBuilder.append(fieldError.getDefaultMessage());
      stringBuilder.append(". \n");
    }

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .message(stringBuilder.toString())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  private void printLog(String logMessage, LogLevel logLevel) {
    switch (logLevel) {
      case ERROR -> logger.error(logMessage);
      case INFO -> logger.info(logMessage);
      case WARN -> logger.warn(logMessage);
      default -> logger.debug(logMessage);
    }
  }
}
