package io.perfume.api.sample.adapter.in.http.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class CustomSampleException extends CustomHttpException  {

    public CustomSampleException() {
        super(HttpStatus.I_AM_A_TEAPOT, "I AM A TEAPOT", "Sample exception log message", LogLevel.ERROR);
    }
}
