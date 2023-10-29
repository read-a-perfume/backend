package io.perfume.api.review.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class UpdateReviewCommentPermissionDeniedException extends CustomHttpException {

  public UpdateReviewCommentPermissionDeniedException(Long userId, Long reviewId) {
    super(HttpStatus.FORBIDDEN, "본인 댓글이 아닌 경우 수정할 수 없습니다.",
        "잘못된 리뷰 수정 요청 userId=" + userId + ", reviewId=" + reviewId, LogLevel.ERROR);
  }
}
