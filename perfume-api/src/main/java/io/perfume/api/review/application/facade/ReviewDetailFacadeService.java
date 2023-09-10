package io.perfume.api.review.application.facade;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import io.perfume.api.review.application.in.GetReviewTagUseCase;
import io.perfume.api.review.application.in.GetReviewsUseCase;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.in.dto.ReviewTagResult;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReviewDetailFacadeService {

  private final GetReviewsUseCase getReviewsUseCase;

  private final FindUserUseCase findUserUseCase;

  private final FindFileUseCase findFileUseCase;

  private final GetReviewTagUseCase getReviewTagUseCase;

  public ReviewDetailFacadeService(
      GetReviewsUseCase getReviewsUseCase,
      FindUserUseCase findUserUseCase,
      FindFileUseCase findFileUseCase,
      GetReviewTagUseCase getReviewTagUseCase
  ) {
    this.getReviewsUseCase = getReviewsUseCase;
    this.findUserUseCase = findUserUseCase;
    this.findFileUseCase = findFileUseCase;
    this.getReviewTagUseCase = getReviewTagUseCase;
  }

  public List<ReviewDetailResult> getPaginatedReviews(int page, int size) {
    final var reviews =
        getReviewsUseCase
            .getPaginatedReviews(page, size)
            .stream()
            .toList();

    final var authorIds = reviews.stream().map(ReviewResult::authorId).toList();
    final var authors = getAuthorsMap(authorIds);

    final var reviewIds = reviews.stream().map(ReviewResult::id).toList();
    final var tags = getReviewTagsMap(reviewIds);

    return reviews.stream().map(mapReviewDetailResult(authors, tags)).toList();
  }

  private Map<Long, UserResult> getAuthorsMap(List<Long> authorIds) {
    return findUserUseCase
        .findUsersByIds(authorIds)
        .stream()
        .map(user -> Map.entry(user.id(), user))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private Map<Long, List<ReviewTagResult>> getReviewTagsMap(final List<Long> reviewIds) {
    return getReviewTagUseCase.getReviewsTags(reviewIds).stream()
        .collect(Collectors.groupingBy(ReviewTagResult::reviewId));
  }

  private Function<ReviewResult, ReviewDetailResult> mapReviewDetailResult(
      Map<Long, UserResult> usersMap,
      Map<Long, List<ReviewTagResult>> tagsMap) {
    return review -> {
      final var author = usersMap.get(review.authorId());
      final var tags = tagsMap.get(review.id()).stream().map(ReviewTagResult::name).toList();

      return ReviewDetailResult.from(review, author, tags);
    };
  }
}
