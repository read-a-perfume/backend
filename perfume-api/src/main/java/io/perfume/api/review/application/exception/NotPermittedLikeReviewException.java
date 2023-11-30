package io.perfume.api.review.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotPermittedLikeReviewException extends CustomHttpException {

  public NotPermittedLikeReviewException(long userId, long reviewId) {
    super(HttpStatus.NOT_FOUND, "허용되지 않는 작업입니다.", "본인 리뷰에 좋아요 요청 reviewId=" + reviewId + ", userId=" + userId, LogLevel.ERROR);
  }
}
