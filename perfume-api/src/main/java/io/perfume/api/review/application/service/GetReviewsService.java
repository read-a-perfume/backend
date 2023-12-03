package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.GetReviewsUseCase;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.out.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetReviewsService implements GetReviewsUseCase {

  private final ReviewQueryRepository reviewQueryRepository;
  private final ReviewLikeQueryRepository reviewLikeQueryRepository;
  private final ReviewCommentQueryRepository reviewCommentQueryRepository;

  @Override
  public List<ReviewResult> getPaginatedReviews(long page, long size) {
    return reviewQueryRepository.findByPage(page, size).stream().map(ReviewResult::from).toList();
  }

  @Override
  public Optional<ReviewResult> getReview(long reviewId) {
    return reviewQueryRepository.findById(reviewId).map(ReviewResult::from);
  }

  @Override
  public long getLikeCount(long reviewId) {
    return reviewLikeQueryRepository.countByReviewId(reviewId);
  }

  @Override
  public long getCommentCount(long reviewId) {
    return reviewCommentQueryRepository.countByReviewId(reviewId);
  }
}
