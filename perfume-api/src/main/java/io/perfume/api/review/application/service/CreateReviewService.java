package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.Tag;
import java.time.LocalDateTime;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReviewService implements CreateReviewUseCase {

  private final ReviewRepository reviewRepository;

  private final TagQueryRepository tagRepository;

  public CreateReviewService(ReviewRepository reviewRepository, TagQueryRepository tagRepository) {
    this.reviewRepository = reviewRepository;
    this.tagRepository = tagRepository;
  }

  @Override
  @Transactional
  public ReviewResult create(Long authorId, CreateReviewCommand command) {
    LocalDateTime now = LocalDateTime.now();
    List<Tag> tags = tagRepository.findByIds(command.tags());

    Review createdReview = reviewRepository.save(createReview(authorId, command, now, tags));

    return new ReviewResult(createdReview.getId());
  }

  @NotNull
  private static Review createReview(Long authorId, CreateReviewCommand command, LocalDateTime now,
                                  List<Tag> tags) {
    Review createReview = Review.create(command.feeling(),
        command.situation(), command.strength(), command.duration(), command.season(),
        command.perfumeId(), authorId, now);
    createReview.addTags(tags);

    return createReview;
  }
}
