package io.perfume.api.user.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class FailedLeaveException extends CustomHttpException {
    public FailedLeaveException(String message, String logMessage)
    {
        super(HttpStatus.UNAUTHORIZED, message, logMessage, LogLevel.WARN);
    }
}
