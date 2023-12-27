package io.perfume.api.review.application.facade;

import dto.repository.CursorPagination;
import io.perfume.api.common.notify.EventPublisher;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailCommand;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailResult;
import io.perfume.api.review.application.facade.dto.ReviewCommentEvent;
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
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        GetReviewCountUseCase,
        GetPerfumeReviewsUseCase,
        GetUserReviewsUseCase {

  private final ReviewService reviewService;
  private final ReviewLikeService reviewLikeService;
  private final ReviewCommentService reviewCommentService;
  private final ReviewTagService reviewTagService;
  private final ReviewThumbnailService reviewThumbnailService;
  private final FindUserUseCase findUserUseCase;
  private final EventPublisher eventPublisher;
  private final FindFileUseCase findFileUseCase;

  @Override
  public CustomPage<ReviewDetailResult> getUserReviews(
      final long userId, final int page, final int size) {
    final CustomPage<ReviewResult> reviews =
        reviewService.getPaginatedUserReviews(
            userId, new GetPaginatedUserReviewsCommand(page, size));
    final List<ReviewDetailResult> items = transformToReviewDetailResults(reviews.getContent());

    return new CustomPage<>(items, reviews);
  }

  @Override
  public CustomPage<ReviewDetailResult> getPaginatedReviews(final int page, final int size) {
    final CustomPage<ReviewResult> reviews =
        reviewService.getPaginatedReviews(new GetPaginatedReviewsCommand(page, size));
    final List<ReviewDetailResult> items = transformToReviewDetailResults(reviews.getContent());

    return new CustomPage<>(items, reviews);
  }

  private List<ReviewDetailResult> transformToReviewDetailResults(final List<ReviewResult> items) {
    final Map<Long, UserResult> authors = getReviewAuthorsMap(items);
    final Map<Long, List<ReviewTagResult>> tags = getReviewTagsMap(items);
    final Map<Long, ReviewCommentCount> commentCountsMap = getReviewCommentMap(items);
    final Map<Long, ReviewLikeCount> likeCountsMap = getReviewLikeCountMap(items);

    return items.stream()
        .map(createReviewDetailResultMapper(authors, tags, commentCountsMap, likeCountsMap))
        .toList();
  }

  private Map<Long, FileResult> getUserThumbnailsMap(final Map<Long, UserResult> usersMap) {
    final List<Long> fileIds = usersMap.values().stream().map(UserResult::thumbnailId).toList();
    return findFileUseCase.findFilesByIds(fileIds).stream()
        .map(file -> Map.entry(file.id(), file))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));
  }

  private Map<Long, ReviewLikeCount> getReviewLikeCountMap(final List<ReviewResult> items) {
    final List<Long> reviewIds = items.stream().map(ReviewResult::id).toList();
    final List<ReviewLikeCount> likeCounts = reviewLikeService.getReviewLikeCount(reviewIds);
    return likeCounts.stream()
        .collect(Collectors.toMap(ReviewLikeCount::reviewId, Function.identity(), (a, b) -> a));
  }

  private Map<Long, ReviewCommentCount> getReviewCommentMap(final List<ReviewResult> items) {
    final List<Long> reviewIds = items.stream().map(ReviewResult::id).toList();
    final List<ReviewCommentCount> commentCounts =
        reviewCommentService.getReviewCommentCount(reviewIds);
    return commentCounts.stream()
        .collect(Collectors.toMap(ReviewCommentCount::reviewId, Function.identity(), (a, b) -> a));
  }

  private Map<Long, UserResult> getReviewAuthorsMap(final List<ReviewResult> items) {
    final List<Long> userIds = items.stream().map(ReviewResult::authorId).toList();
    return getAuthorsMap(userIds);
  }

  private Map<Long, UserResult> getAuthorsMap(final List<Long> userIds) {
    return findUserUseCase.findUsersByIds(userIds).stream()
        .map(user -> Map.entry(user.id(), user))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));
  }

  private Map<Long, List<ReviewTagResult>> getReviewTagsMap(final List<ReviewResult> items) {
    final List<Long> reviewIds = items.stream().map(ReviewResult::id).toList();
    return reviewTagService.getReviewsTags(reviewIds).stream()
        .collect(Collectors.groupingBy(ReviewTagResult::reviewId));
  }

  private Function<ReviewResult, ReviewDetailResult> createReviewDetailResultMapper(
      Map<Long, UserResult> usersMap,
      Map<Long, List<ReviewTagResult>> tagsMap,
      Map<Long, ReviewCommentCount> commentCountsMap,
      Map<Long, ReviewLikeCount> likeCountsMap) {
    return review -> {
      final var author = usersMap.getOrDefault(review.authorId(), UserResult.EMPTY);
      final var tags =
          tagsMap.getOrDefault(review.id(), Collections.emptyList()).stream()
              .map(ReviewTagResult::name)
              .toList();
      final var commentCount =
          commentCountsMap.getOrDefault(review.id(), ReviewCommentCount.ZERO).count();
      final var likeCount = likeCountsMap.getOrDefault(review.id(), ReviewLikeCount.ZERO).count();

      return ReviewDetailResult.from(review, author, tags, likeCount, commentCount);
    };
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
  @Transactional
  public ReviewCommentResult createComment(CreateReviewCommentCommand command, LocalDateTime now) {
    ReviewResult reviewResult =
        reviewService
            .getReview(command.reviewId())
            .orElseThrow(() -> new NotFoundReviewException(command.reviewId()));
    ReviewCommentResult comment = reviewCommentService.createComment(command, now);

    if (!Objects.equals(command.userId(), reviewResult.authorId())) {
      ReviewCommentEvent reviewCommentEvent = new ReviewCommentEvent(reviewResult.authorId());
      eventPublisher.publishEvent(reviewCommentEvent);
    }
    return comment;
  }

  @Override
  public void deleteComment(long id, long userId, LocalDateTime now) {
    reviewCommentService.deleteComment(id, userId, now);
  }

  @Override
  public CursorPagination<ReviewCommentDetailResult> getReviewComments(
      final ReviewCommentDetailCommand command) {
    final CursorPagination<ReviewComment> reviewComments =
        reviewCommentService.getReviewComments(command.toGetReviewCommentsCommand());
    final Map<Long, UserResult> authorsMap = getCommentAuthorsMap(reviewComments.getItems());
    final Map<Long, FileResult> thumbnailsMap = getUserThumbnailsMap(authorsMap);
    final List<ReviewCommentDetailResult> result =
        reviewComments.getItems().stream()
            .map(createReviewCommentDetailResultMapper(authorsMap, thumbnailsMap))
            .toList();

    return CursorPagination.of(result, reviewComments);
  }

  private Map<Long, UserResult> getCommentAuthorsMap(final List<ReviewComment> items) {
    final List<Long> userIds = items.stream().map(ReviewComment::getUserId).toList();
    return getAuthorsMap(userIds);
  }

  private Function<ReviewComment, ReviewCommentDetailResult> createReviewCommentDetailResultMapper(
      final Map<Long, UserResult> usersMap, final Map<Long, FileResult> thumbnailsMap) {
    return comment -> {
      final UserResult author = usersMap.getOrDefault(comment.getUserId(), UserResult.EMPTY);
      final FileResult thumbnail =
          thumbnailsMap.getOrDefault(author.thumbnailId(), FileResult.DEFAULT_THUMBNAIL);
      return ReviewCommentDetailResult.from(comment, author, thumbnail);
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

  @Override
  public CustomPage<ReviewDetailResult> getPaginatedPerfumeReviews(
      long perfumeId, int page, int size) {
    final Pageable pageable = Pageable.ofSize(size).withPage(page);
    final CustomPage<ReviewResult> reviews =
        reviewService.getReviewsByPerfumeId(perfumeId, pageable);
    final List<ReviewResult> items = reviews.getContent();

    return new CustomPage<>(transformToReviewDetailResults(items), reviews);
  }
}
