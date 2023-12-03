package io.perfume.api.review.application.facade;

import dto.repository.CursorPagination;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailCommand;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailResult;
import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import io.perfume.api.review.application.facade.dto.ReviewViewDetailResult;
import io.perfume.api.review.application.in.GetReviewCommentsUseCase;
import io.perfume.api.review.application.in.GetReviewTagUseCase;
import io.perfume.api.review.application.in.GetReviewsUseCase;
import io.perfume.api.review.application.in.dto.GetReviewCommentsCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.in.dto.ReviewTagResult;
import io.perfume.api.review.domain.ReviewComment;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewDetailFacadeService {

  private final GetReviewsUseCase getReviewsUseCase;
  private final FindUserUseCase findUserUseCase;
  private final FindFileUseCase findFileUseCase;
  private final GetReviewTagUseCase getReviewTagUseCase;
  private final GetReviewCommentsUseCase getReviewCommentsUseCase;

  public ReviewViewDetailResult getReviewDetail(long reviewId) {
    final var review =
        getReviewsUseCase
            .getReview(reviewId)
            .orElseThrow(() -> new NotFoundReviewException(reviewId));
    final var author = findUserUseCase.findUserById(review.authorId()).orElse(UserResult.EMPTY);
    final var tags =
        getReviewTagUseCase.getReviewTags(reviewId).stream().map(ReviewTagResult::name).toList();
    final var likeCount = getReviewsUseCase.getLikeCount(reviewId);
    final var commentCount = getReviewsUseCase.getCommentCount(reviewId);

    return ReviewViewDetailResult.from(
        review, author, tags, Collections.emptyList(), likeCount, commentCount);
  }

  public List<ReviewDetailResult> getPaginatedReviews(long page, long size) {
    final var reviews = getReviewsUseCase.getPaginatedReviews(page, size).stream().toList();

    final var authorIds = reviews.stream().map(ReviewResult::authorId).toList();
    final var authors = getAuthorsMap(authorIds);

    final var reviewIds = reviews.stream().map(ReviewResult::id).toList();
    final var tags = getReviewTagsMap(reviewIds);

    return reviews.stream().map(mapReviewDetailResult(authors, tags)).toList();
  }

  public CursorPagination<ReviewCommentDetailResult> getReviewComments(
      final ReviewCommentDetailCommand command) {
    final var comments =
        getReviewCommentsUseCase.getReviewComments(
            new GetReviewCommentsCommand(
                command.size(), command.before(), command.after(), command.reviewId()));

    final var userIds = comments.getItems().stream().map(ReviewComment::getUserId).toList();
    final var authorsMap = getAuthorsMap(userIds);

    final var result =
        comments.getItems().stream().map(mapReviewCommentDetailResult(authorsMap)).toList();

    return CursorPagination.of(result, comments.hasNext(), comments.hasPrevious());
  }

  private Map<Long, UserResult> getAuthorsMap(List<Long> authorIds) {
    return findUserUseCase.findUsersByIds(authorIds).stream()
        .map(user -> Map.entry(user.id(), user))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private Map<Long, List<ReviewTagResult>> getReviewTagsMap(final List<Long> reviewIds) {
    return getReviewTagUseCase.getReviewsTags(reviewIds).stream()
        .collect(Collectors.groupingBy(ReviewTagResult::reviewId));
  }

  private Function<ReviewResult, ReviewDetailResult> mapReviewDetailResult(
      Map<Long, UserResult> usersMap, Map<Long, List<ReviewTagResult>> tagsMap) {
    return review -> {
      final var author = usersMap.getOrDefault(review.authorId(), UserResult.EMPTY);
      final var tags =
          tagsMap.getOrDefault(review.id(), Collections.emptyList()).stream()
              .map(ReviewTagResult::name)
              .toList();

      return ReviewDetailResult.from(review, author, tags);
    };
  }

  private Function<ReviewComment, ReviewCommentDetailResult> mapReviewCommentDetailResult(
      Map<Long, UserResult> usersMap) {
    return comment -> {
      final var author = usersMap.getOrDefault(comment.getUserId(), UserResult.EMPTY);
      return ReviewCommentDetailResult.from(comment, author);
    };
  }
}
