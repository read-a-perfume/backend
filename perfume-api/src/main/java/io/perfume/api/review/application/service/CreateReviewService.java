package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.AddReviewTagUseCase;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.domain.Review;
import java.time.LocalDateTime;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReviewService implements CreateReviewUseCase {

  private final ReviewRepository reviewRepository;

  private final AddReviewTagUseCase addReviewTagUseCase;

  public CreateReviewService(ReviewRepository reviewRepository,
                             AddReviewTagUseCase addReviewTagUseCase) {
    this.reviewRepository = reviewRepository;
    this.addReviewTagUseCase = addReviewTagUseCase;
  }

  @NotNull
  private static Review createReview(Long authorId, CreateReviewCommand command,
                                     LocalDateTime now) {
    return Review.create(
        command.feeling(),
        command.situation(),
        command.strength(),
        command.duration(),
        command.season(),
        command.perfumeId(),
        authorId,
        now
    );
  }

  @Override
  @Transactional
  public ReviewResult create(Long authorId, CreateReviewCommand command) {
    LocalDateTime now = LocalDateTime.now();
    Review createdReview = reviewRepository.save(createReview(authorId, command, now));
    addReviewTagUseCase.addTags(createdReview.getId(), command.tags());

    return ReviewResult.from(createdReview);
  }
}
