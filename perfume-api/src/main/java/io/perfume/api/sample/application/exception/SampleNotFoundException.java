package io.perfume.api.sample.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class SampleNotFoundException extends CustomHttpException {
    public SampleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "sample does not exist", "sample does not exist", LogLevel.WARN);
    }
}
