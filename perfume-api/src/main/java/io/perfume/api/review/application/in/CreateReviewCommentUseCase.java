package io.perfume.api.review.application.in;

import io.perfume.api.review.application.in.dto.CreateReviewCommentCommand;
import io.perfume.api.review.application.in.dto.ReviewCommentResult;
import java.time.LocalDateTime;

public interface CreateReviewCommentUseCase {

  ReviewCommentResult createComment(CreateReviewCommentCommand command, LocalDateTime now);
}
