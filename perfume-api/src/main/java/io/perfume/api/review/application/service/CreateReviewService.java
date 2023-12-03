package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.AddReviewTagUseCase;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.domain.Review;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReviewService implements CreateReviewUseCase {

  private final ReviewRepository reviewRepository;

  private final AddReviewTagUseCase addReviewTagUseCase;

  public CreateReviewService(
      ReviewRepository reviewRepository, AddReviewTagUseCase addReviewTagUseCase) {
    this.reviewRepository = reviewRepository;
    this.addReviewTagUseCase = addReviewTagUseCase;
  }

  @NotNull
  private static Review createReview(Long authorId, CreateReviewCommand command) {
    return Review.create(
        command.shortReview(),
        command.situation(),
        command.strength(),
        command.duration(),
        command.dayType(),
        command.perfumeId(),
        authorId,
        command.season(),
        command.now());
  }

  @Override
  @Transactional
  public ReviewResult create(Long authorId, CreateReviewCommand command) {
    Review createdReview = reviewRepository.save(createReview(authorId, command));
    addReviewTagUseCase.addTags(createdReview.getId(), command.tags());

    return ReviewResult.from(createdReview);
  }
}
