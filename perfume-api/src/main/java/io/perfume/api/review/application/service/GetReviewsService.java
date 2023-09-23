package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.GetReviewsUseCase;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetReviewsService implements GetReviewsUseCase {

  private final ReviewQueryRepository reviewQueryRepository;

  public GetReviewsService(ReviewQueryRepository reviewQueryRepository) {
    this.reviewQueryRepository = reviewQueryRepository;
  }

  @Override
  public List<ReviewResult> getPaginatedReviews(long page, long size) {
    return reviewQueryRepository
        .findByPage(page, size)
        .stream()
        .map(ReviewResult::from)
        .toList();
  }
}
