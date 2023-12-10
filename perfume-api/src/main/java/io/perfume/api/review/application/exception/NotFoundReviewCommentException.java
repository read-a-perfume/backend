package io.perfume.api.review.application.exception;

import io.perfume.api.base.CustomHttpException;
import io.perfume.api.base.LogLevel;
import org.springframework.http.HttpStatus;

public class NotFoundReviewCommentException extends CustomHttpException {

  public NotFoundReviewCommentException(long reviewCommentId) {
    super(
        HttpStatus.NOT_FOUND,
        "존재하지 않는 리뷰 댓글입니다.",
        "존재하지 않는 댓글 삭제 요청 reviewCommentId=" + reviewCommentId,
        LogLevel.ERROR);
  }
}
