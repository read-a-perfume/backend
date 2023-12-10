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
import io.perfume.api.review.domain.ReviewLike;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.service.FindUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDetailFacadeService implements
        CreateReviewCommentUseCase,
        DeleteReviewCommentUseCase,
        GetReviewCommentsUseCase,
        UpdateReviewCommentUseCase,
        ReviewLikeUseCase,
        CreateReviewUseCase,
        DeleteReviewUseCase,
        GetReviewsUseCase,
        GetReviewInViewUseCase,
        ReviewStatisticUseCase {

    private final ReviewService reviewService;
    private final ReviewLikeService reviewLikeService;
    private final ReviewCommentService reviewCommentService;
    private final ReviewTagService reviewTagService;
    private final ReviewThumbnailService reviewThumbnailService;
    private final FindUserService findUserUseCase;

    @Override
    public List<ReviewDetailResult> getPaginatedReviews(long page, long size) {
        final List<ReviewResult> reviews =
                reviewService
                        .getPaginatedReviews(page, size)
                        .stream()
                        .toList();

        final List<Long> authorIds = reviews.stream().map(ReviewResult::authorId).toList();
        final Map<Long, UserResult> authors = getAuthorsMap(authorIds);

        final List<Long> reviewIds = reviews.stream().map(ReviewResult::id).toList();
        final Map<Long, List<ReviewTagResult>> tags = getReviewTagsMap(reviewIds);

        final List<ReviewCommentCount> commentCounts = reviewCommentService.getReviewCommentCount(reviewIds);
        final Map<Long, ReviewCommentCount> commentCountsMap = commentCounts.stream()
                .collect(Collectors.toMap(ReviewCommentCount::reviewId, Function.identity()));

        final List<ReviewLikeCount> likeCounts = reviewLikeService.getReviewLikeCount(reviewIds);
        final Map<Long, ReviewLikeCount> likeCountsMap = likeCounts.stream()
                .collect(Collectors.toMap(ReviewLikeCount::reviewId, Function.identity()));

        return reviews.stream().map(mapReviewDetailResult(authors, tags, commentCountsMap, likeCountsMap)).toList();
    }

    private Map<Long, UserResult> getAuthorsMap(List<Long> authorIds) {
        return findUserUseCase
                .findUsersByIds(authorIds)
                .stream()
                .map(user -> Map.entry(user.id(), user))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Long, List<ReviewTagResult>> getReviewTagsMap(final List<Long> reviewIds) {
        return reviewTagService.getReviewsTags(reviewIds).stream()
                .collect(Collectors.groupingBy(ReviewTagResult::reviewId));
    }

    private Function<ReviewResult, ReviewDetailResult> mapReviewDetailResult(
            Map<Long, UserResult> usersMap,
            Map<Long, List<ReviewTagResult>> tagsMap,
            Map<Long, ReviewCommentCount> commentCountsMap,
            Map<Long, ReviewLikeCount> likeCountsMap) {
        return review -> {
            final var author = usersMap.getOrDefault(review.authorId(), UserResult.EMPTY);
            final var tags =
                    tagsMap
                            .getOrDefault(review.id(), Collections.emptyList())
                            .stream()
                            .map(ReviewTagResult::name)
                            .toList();
            final var commentCount = commentCountsMap.getOrDefault(review.id(), ReviewCommentCount.ZERO).count();
            final var likeCount = likeCountsMap.getOrDefault(review.id(), ReviewLikeCount.ZERO).count();

            return ReviewDetailResult.from(review, author, tags, likeCount, commentCount);
        };
    }

    @Override
    public ReviewViewDetailResult getReviewDetail(long reviewId) {
        final var review = reviewService
                .getReview(reviewId)
                .orElseThrow(() -> new NotFoundReviewException(reviewId));
        final var author = findUserUseCase.findUserById(review.authorId()).orElse(UserResult.EMPTY);
        final var tags = reviewTagService.getReviewTags(reviewId).stream()
                .map(ReviewTagResult::name)
                .toList();
        final var likeCount = reviewService.getLikeCount(reviewId);
        final var commentCount = reviewService.getCommentCount(reviewId);

        return ReviewViewDetailResult.from(review, author, tags, Collections.emptyList(), likeCount,
                commentCount);
    }

    @Override
    public ReviewCommentResult createComment(CreateReviewCommentCommand command, LocalDateTime now) {
        reviewService.getReview(command.reviewId())
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
        final var comments = reviewCommentService.getReviewComments(
                new GetReviewCommentsCommand(command.size(), command.before(), command.after(),
                        command.reviewId()));

        final var userIds = comments.getItems().stream().map(ReviewComment::getUserId).toList();
        final var authorsMap = getAuthorsMap(userIds);

        final var result = comments.getItems().stream()
                .map(mapReviewCommentDetailResult(authorsMap))
                .toList();

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
        reviewThumbnailService.addThumbnails(reviewDetailResult.id(), command.thumbnailIds(), command.now());
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
}
