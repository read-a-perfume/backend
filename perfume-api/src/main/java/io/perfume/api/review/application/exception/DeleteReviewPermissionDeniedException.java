package io.perfume.api.review.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class DeleteReviewPermissionDeniedException extends CustomHttpException {

  public DeleteReviewPermissionDeniedException(Long userId, Long reviewId) {
    super(HttpStatus.FORBIDDEN, "리뷰를 삭제할 수 없습니다.",
        "잘못된 리뷰 삭제 요청 userId=" + userId + ", reviewId=" + reviewId, LogLevel.ERROR);
  }
}
