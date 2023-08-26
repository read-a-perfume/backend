package io.perfume.api.review.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundReviewException extends CustomHttpException {

  public NotFoundReviewException(long reviewId) {
    super(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰 정보입니다.", "존재하지 않는 리뷰 삭제 요청" + reviewId, LogLevel.ERROR);
  }
}
