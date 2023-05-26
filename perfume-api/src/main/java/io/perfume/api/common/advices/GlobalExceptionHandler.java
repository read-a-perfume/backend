package io.perfume.api.common.advices;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<?> handleCustomException(CustomHttpException exception) {
        printLog(exception.getMessage(), exception.getLogLevel());

        Map<String, Object> error = Map.of(
                "status", exception.getHttpStatus().getReasonPhrase(),
                "statusCode", exception.getHttpStatus().value(),
                "error", exception.getMessage()
        );
        return ResponseEntity.status(exception.getHttpStatus()).body(error);
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
