package io.perfume.api.review.application.service;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.review.application.in.GetReviewCommentsUseCase;
import io.perfume.api.review.application.in.dto.GetReviewCommentsCommand;
import io.perfume.api.review.application.out.ReviewCommentQueryRepository;
import io.perfume.api.review.domain.ReviewComment;
import org.springframework.stereotype.Service;

@Service
public class GetReviewCommentsService implements GetReviewCommentsUseCase {

  private final ReviewCommentQueryRepository reviewCommentQueryRepository;

  public GetReviewCommentsService(ReviewCommentQueryRepository reviewCommentQueryRepository) {
    this.reviewCommentQueryRepository = reviewCommentQueryRepository;
  }

  @Override
  public CursorPagination<ReviewComment> getReviewComments(GetReviewCommentsCommand command) {
    final var pageable =
        new CursorPageable<>(command.size(), command.getDirection(), command.getCursor());
    return reviewCommentQueryRepository.findByReviewId(pageable, command.reviewId());
  }
}
