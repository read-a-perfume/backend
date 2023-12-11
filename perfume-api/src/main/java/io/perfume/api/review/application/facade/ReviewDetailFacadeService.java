package io.perfume.api.review.application.facade;

import dto.repository.CursorPagination;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailCommand;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailResult;
import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import io.perfume.api.review.application.facade.dto.ReviewViewDetailResult;
import io.perfume.api.review.application.in.comment.CreateReviewCommentUseCase;
import io.perfume.api.review.application.in.comment.DeleteReviewCommentUseCase;
import io.perfume.api.review.application.in.comment.GetReviewCommentsUseCase;
import io.perfume.api.review.application.in.comment.UpdateReviewCommentUseCase;
import io.perfume.api.review.application.in.dto.*;
import io.perfume.api.review.application.in.like.ReviewLikeUseCase;
import io.perfume.api.review.application.in.review.*;
import io.perfume.api.review.application.out.comment.dto.ReviewCommentCount;
import io.perfume.api.review.application.out.like.dto.ReviewLikeCount;
import io.perfume.api.review.application.service.*;
import io.perfume.api.review.domain.ReviewComment;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDetailFacadeService
    implements CreateReviewCommentUseCase,
        DeleteReviewCommentUseCase,
        GetReviewCommentsUseCase,
        UpdateReviewCommentUseCase,
        ReviewLikeUseCase,
        CreateReviewUseCase,
        DeleteReviewUseCase,
        GetReviewsUseCase,
        GetReviewInViewUseCase,
        ReviewStatisticUseCase,
        GetReviewCountUseCase {

  private final ReviewService reviewService;
  private final ReviewLikeService reviewLikeService;
  private final ReviewCommentService reviewCommentService;
  private final ReviewTagService reviewTagService;
  private final ReviewThumbnailService reviewThumbnailService;
  private final FindUserUseCase findUserUseCase;

  @Override
  public List<ReviewDetailResult> getPaginatedReviews(final long page, final long size) {
    final List<ReviewResult> reviews = fetchReviews(page, size);
    final Map<Long, UserResult> authors = fetchAuthors(reviews);
    final Map<Long, List<ReviewTagResult>> tags = fetchTags(reviews);
    final Map<Long, ReviewCommentCount> commentCountsMap = fetchCommentsCount(reviews);
    final Map<Long, ReviewLikeCount> likeCountsMap = fetchLikesCount(reviews);

    return mapReviewDetailResults(reviews, authors, tags, commentCountsMap, likeCountsMap);
  }

  private List<ReviewResult> fetchReviews(final long page, final long size) {
    return reviewService.getPaginatedReviews(page, size).stream().toList();
  }

  private Map<Long, UserResult> fetchAuthors(final List<ReviewResult> reviews) {
    final List<Long> authorIds = reviews.stream().map(ReviewResult::authorId).toList();

    return getAuthorsMap(authorIds);
  }

  private Map<Long, List<ReviewTagResult>> fetchTags(final List<ReviewResult> reviews) {
    final List<Long> reviewIds = reviews.stream().map(ReviewResult::id).toList();

    return getReviewTagsMap(reviewIds);
  }

  private Map<Long, ReviewCommentCount> fetchCommentsCount(final List<ReviewResult> reviews) {
    final List<Long> reviewIds = reviews.stream().map(ReviewResult::id).toList();
    final List<ReviewCommentCount> commentCounts =
        reviewCommentService.getReviewCommentCount(reviewIds);

    return commentCounts.stream()
        .collect(Collectors.toMap(ReviewCommentCount::reviewId, Function.identity()));
  }

  private Map<Long, ReviewLikeCount> fetchLikesCount(final List<ReviewResult> reviews) {
    final List<Long> reviewIds = reviews.stream().map(ReviewResult::id).toList();
    final List<ReviewLikeCount> likeCounts = reviewLikeService.getReviewLikeCount(reviewIds);

    return likeCounts.stream()
        .collect(Collectors.toMap(ReviewLikeCount::reviewId, Function.identity()));
  }

  private List<ReviewDetailResult> mapReviewDetailResults(
      final List<ReviewResult> reviews,
      final Map<Long, UserResult> authors,
      final Map<Long, List<ReviewTagResult>> tags,
      final Map<Long, ReviewCommentCount> commentCountsMap,
      final Map<Long, ReviewLikeCount> likeCountsMap) {
    return reviews.stream()
        .map(mapReviewDetailResult(authors, tags, commentCountsMap, likeCountsMap))
        .toList();
  }

  private Map<Long, UserResult> getAuthorsMap(final List<Long> authorIds) {
    return findUserUseCase.findUsersByIds(authorIds).stream()
        .map(user -> Map.entry(user.id(), user))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));
  }

  private Map<Long, List<ReviewTagResult>> getReviewTagsMap(final List<Long> reviewIds) {
    return reviewTagService.getReviewsTags(reviewIds).stream()
        .collect(Collectors.groupingBy(ReviewTagResult::reviewId));
  }

  private Function<ReviewResult, ReviewDetailResult> mapReviewDetailResult(
      final Map<Long, UserResult> usersMap,
      final Map<Long, List<ReviewTagResult>> tagsMap,
      final Map<Long, ReviewCommentCount> commentCountsMap,
      final Map<Long, ReviewLikeCount> likeCountsMap) {
    return review ->
        processReviewResult(review, usersMap, tagsMap, commentCountsMap, likeCountsMap);
  }

  private UserResult getReviewUser(
      final ReviewResult review, final Map<Long, UserResult> usersMap) {
    return usersMap.getOrDefault(review.authorId(), UserResult.EMPTY);
  }

  private List<String> getReviewTags(
      final ReviewResult review, final Map<Long, List<ReviewTagResult>> tagsMap) {
    return tagsMap.getOrDefault(review.id(), Collections.emptyList()).stream()
        .map(ReviewTagResult::name)
        .toList();
  }

  private long getReviewCommentCount(
      final ReviewResult review, final Map<Long, ReviewCommentCount> commentCountsMap) {
    return commentCountsMap.getOrDefault(review.id(), ReviewCommentCount.ZERO).count();
  }

  private long getReviewLikeCount(
      final ReviewResult review, final Map<Long, ReviewLikeCount> likeCountsMap) {
    return likeCountsMap.getOrDefault(review.id(), ReviewLikeCount.ZERO).count();
  }

  private ReviewDetailResult processReviewResult(
      final ReviewResult review,
      final Map<Long, UserResult> usersMap,
      final Map<Long, List<ReviewTagResult>> tagsMap,
      final Map<Long, ReviewCommentCount> commentCountsMap,
      final Map<Long, ReviewLikeCount> likeCountsMap) {
    final UserResult author = getReviewUser(review, usersMap);
    final List<String> keywords = getReviewTags(review, tagsMap);
    final long commentCount = getReviewCommentCount(review, commentCountsMap);
    final long likeCount = getReviewLikeCount(review, likeCountsMap);

    return ReviewDetailResult.from(review, author, keywords, likeCount, commentCount);
  }

  @Override
  public ReviewViewDetailResult getReviewDetail(long reviewId) {
    final var review =
        reviewService.getReview(reviewId).orElseThrow(() -> new NotFoundReviewException(reviewId));
    final var author = findUserUseCase.findUserById(review.authorId()).orElse(UserResult.EMPTY);
    final var tags =
        reviewTagService.getReviewTags(reviewId).stream().map(ReviewTagResult::name).toList();
    final var likeCount = reviewService.getLikeCount(reviewId);
    final var commentCount = reviewService.getCommentCount(reviewId);

    return ReviewViewDetailResult.from(
        review, author, tags, Collections.emptyList(), likeCount, commentCount);
  }

  @Override
  public ReviewCommentResult createComment(CreateReviewCommentCommand command, LocalDateTime now) {
    reviewService
        .getReview(command.reviewId())
        .orElseThrow(() -> new NotFoundReviewException(command.reviewId()));

    return reviewCommentService.createComment(command, now);
  }

  @Override
  public void deleteComment(long id, long userId, LocalDateTime now) {
    reviewCommentService.deleteComment(id, userId, now);
  }

  @Override
  public CursorPagination<ReviewCommentDetailResult> getReviewComments(
      final ReviewCommentDetailCommand command) {
    final var comments =
        reviewCommentService.getReviewComments(
            new GetReviewCommentsCommand(
                command.size(), command.before(), command.after(), command.reviewId()));

    final var userIds = comments.getItems().stream().map(ReviewComment::getUserId).toList();
    final var authorsMap = getAuthorsMap(userIds);

    final var result =
        comments.getItems().stream().map(mapReviewCommentDetailResult(authorsMap)).toList();

    return CursorPagination.of(result, comments.hasNext(), comments.hasPrevious());
  }

  private Function<ReviewComment, ReviewCommentDetailResult> mapReviewCommentDetailResult(
      Map<Long, UserResult> usersMap) {
    return comment -> {
      final var author = usersMap.getOrDefault(comment.getUserId(), UserResult.EMPTY);
      return ReviewCommentDetailResult.from(comment, author);
    };
  }

  @Override
  public void updateReviewComment(Long userId, Long commentId, String newComment) {
    reviewCommentService.updateReviewComment(userId, commentId, newComment);
  }

  @Override
  public long toggleLikeReview(long userId, long reviewId, LocalDateTime now) {
    return reviewLikeService.toggleLikeReview(userId, reviewId, now);
  }

  @Override
  public ReviewResult create(Long authorId, CreateReviewCommand command) {
    final ReviewResult reviewDetailResult = reviewService.create(authorId, command);
    reviewTagService.addTags(reviewDetailResult.id(), command.keywords());
    reviewThumbnailService.addThumbnails(
        reviewDetailResult.id(), command.thumbnailIds(), command.now());
    return reviewDetailResult;
  }

  @Override
  public void delete(Long userId, Long reviewId, LocalDateTime now) {
    reviewService.delete(userId, reviewId, now);
  }

  @Override
  public ReviewStatisticResult getStatisticByPerfume(Long perfumeId) {
    return reviewService.getStatisticByPerfume(perfumeId);
  }

  @Override
  public Long getReviewCountByUserId(Long userId) {
    return reviewService.getReviewCountByUserId(userId);
  }
}
