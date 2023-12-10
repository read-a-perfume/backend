package io.perfume.api.review.application.service;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.review.application.exception.NotFoundReviewCommentException;
import io.perfume.api.review.application.exception.UpdateReviewCommentPermissionDeniedException;
import io.perfume.api.review.application.in.comment.CreateReviewCommentUseCase;
import io.perfume.api.review.application.in.comment.DeleteReviewCommentUseCase;
import io.perfume.api.review.application.in.comment.GetReviewCommentsUseCase;
import io.perfume.api.review.application.in.comment.UpdateReviewCommentUseCase;
import io.perfume.api.review.application.in.dto.CreateReviewCommentCommand;
import io.perfume.api.review.application.in.dto.GetReviewCommentsCommand;
import io.perfume.api.review.application.in.dto.ReviewCommentResult;
import io.perfume.api.review.application.out.comment.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.comment.ReviewCommentRepository;
import io.perfume.api.review.application.out.review.ReviewQueryRepository;
import io.perfume.api.review.domain.ReviewComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewCommentQueryRepository reviewCommentQueryRepository;

    public ReviewCommentResult createComment(CreateReviewCommentCommand command, LocalDateTime now) {
        final var createdReviewComment = reviewCommentRepository.save(
                ReviewComment.create(command.reviewId(), command.userId(), command.content(), now));
        return ReviewCommentResult.from(createdReviewComment);
    }

    public void deleteComment(long id, long userId, LocalDateTime now) {
        final var reviewComment =
                reviewCommentQueryRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundReviewCommentException(id));
        if (reviewComment.isOwned(userId)) {
            reviewComment.markDelete(now);
        }
        reviewCommentRepository.save(reviewComment);
    }

    public CursorPagination<ReviewComment> getReviewComments(GetReviewCommentsCommand command) {
        final var pageable =
                new CursorPageable<>(command.size(), command.getDirection(), command.getCursor());
        return reviewCommentQueryRepository.findByReviewId(pageable, command.reviewId());
    }

    public void updateReviewComment(Long userId, Long commentId, String newComment) {
        final var comment =
                reviewCommentQueryRepository.findById(commentId)
                        .orElseThrow(() -> new NotFoundReviewCommentException(commentId));

        if (!comment.isOwned(userId)) {
            throw new UpdateReviewCommentPermissionDeniedException(userId, commentId);
        }

        comment.updateComment(newComment);
        reviewCommentRepository.save(comment);
    }
}
