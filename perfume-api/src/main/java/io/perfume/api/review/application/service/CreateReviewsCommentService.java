package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.in.CreateReviewCommentUseCase;
import io.perfume.api.review.application.in.dto.CreateReviewCommentCommand;
import io.perfume.api.review.application.in.dto.ReviewCommentResult;
import io.perfume.api.review.application.out.ReviewCommentRepository;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.domain.ReviewComment;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CreateReviewsCommentService implements CreateReviewCommentUseCase {

  private final ReviewCommentRepository reviewCommentRepository;

  private final ReviewQueryRepository reviewQueryRepository;

  public CreateReviewsCommentService(
      ReviewCommentRepository reviewCommentRepository,
      ReviewQueryRepository reviewQueryRepository) {
    this.reviewCommentRepository = reviewCommentRepository;
    this.reviewQueryRepository = reviewQueryRepository;
  }

  @Override
  public ReviewCommentResult createComment(
      final CreateReviewCommentCommand command, final LocalDateTime now) {
    reviewQueryRepository
        .findById(command.reviewId())
        .orElseThrow(() -> new NotFoundReviewException(command.reviewId()));

    final var createdReviewComment =
        reviewCommentRepository.save(
            ReviewComment.create(command.reviewId(), command.userId(), command.content(), now));
    return ReviewCommentResult.from(createdReviewComment);
  }
}
