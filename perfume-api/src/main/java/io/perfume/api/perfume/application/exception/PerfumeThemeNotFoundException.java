package io.perfume.api.perfume.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class PerfumeThemeNotFoundException extends CustomHttpException {
  public PerfumeThemeNotFoundException() {
    super(
        HttpStatus.NOT_FOUND,
        "테마별 향수 추천 데이터가 존재하지 않습니다.",
        "테마별 향수 추천 데이터가 존재하지 않습니다.",
        LogLevel.WARN);
  }
}
