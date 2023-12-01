package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.GetReviewCountUseCase;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetReviewCountService implements GetReviewCountUseCase {

  private final ReviewQueryRepository reviewQueryRepository;

  public GetReviewCountService(ReviewQueryRepository reviewQueryRepository) {
    this.reviewQueryRepository = reviewQueryRepository;
  }

  @Override
  public Long getReviewCountByUserId(Long userId) {
    return reviewQueryRepository.findReviewCountByUserId(userId);
  }
}
