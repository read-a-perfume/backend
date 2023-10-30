package io.perfume.api.review.adapter.in.http;

import dto.ui.CursorResponse;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewCommentRequestDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewCommentResponseDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewResponseDto;
import io.perfume.api.review.adapter.in.http.dto.DeleteReviewCommentResponseDto;
import io.perfume.api.review.adapter.in.http.dto.DeleteReviewResponseDto;
import io.perfume.api.review.adapter.in.http.dto.GetReviewCommentsRequestDto;
import io.perfume.api.review.adapter.in.http.dto.GetReviewCommentsResponseDto;
import io.perfume.api.review.adapter.in.http.dto.GetReviewsRequestDto;
import io.perfume.api.review.adapter.in.http.dto.GetReviewsResponseDto;
import io.perfume.api.review.application.facade.ReviewDetailFacadeService;
import io.perfume.api.review.application.in.CreateReviewCommentUseCase;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.DeleteReviewCommentUseCase;
import io.perfume.api.review.application.in.DeleteReviewUseCase;
import io.perfume.api.review.application.in.UpdateReviewCommentUseCase;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewController {

  private final CreateReviewUseCase createReviewUseCase;

  private final DeleteReviewUseCase deleteReviewUseCase;

  private final ReviewDetailFacadeService reviewDetailFacadeService;

  private final CreateReviewCommentUseCase createReviewCommentUseCase;

  private final DeleteReviewCommentUseCase deleteReviewCommentUseCase;

  private final UpdateReviewCommentUseCase updateReviewCommentUseCase;

  public ReviewController(CreateReviewUseCase createReviewUseCase,
                          DeleteReviewUseCase deleteReviewUseCase,
                          ReviewDetailFacadeService reviewDetailFacadeService,
                          CreateReviewCommentUseCase createReviewCommentUseCase,
                          DeleteReviewCommentUseCase deleteReviewCommentUseCase,
                          UpdateReviewCommentUseCase updateReviewCommentUseCase
  ) {
    this.createReviewUseCase = createReviewUseCase;
    this.deleteReviewUseCase = deleteReviewUseCase;
    this.reviewDetailFacadeService = reviewDetailFacadeService;
    this.createReviewCommentUseCase = createReviewCommentUseCase;
    this.deleteReviewCommentUseCase = deleteReviewCommentUseCase;
    this.updateReviewCommentUseCase = updateReviewCommentUseCase;
  }

  @GetMapping
  public List<GetReviewsResponseDto> getReviews(
      GetReviewsRequestDto dto
  ) {
    final var results = reviewDetailFacadeService.getPaginatedReviews(dto.offset(), dto.limit());

    return GetReviewsResponseDto.from(results);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public CreateReviewResponseDto createReview(
      @AuthenticationPrincipal User user,
      @RequestBody CreateReviewRequestDto requestDto
  ) {
    var userId = Long.parseLong(user.getUsername());
    var response = createReviewUseCase.create(userId, requestDto.toCommand());

    return new CreateReviewResponseDto(response.id());
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteReviewResponseDto> deleteReview(
      @AuthenticationPrincipal User user,
      @PathVariable Long id
  ) {
    var now = LocalDateTime.now();
    var userId = Long.parseLong(user.getUsername());
    deleteReviewUseCase.delete(userId, id, now);

    return ResponseEntity.ok(new DeleteReviewResponseDto(id));
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/{id}/comments")
  public ResponseEntity<CreateReviewCommentResponseDto> createReviewComment(
      @AuthenticationPrincipal User user,
      @PathVariable Long id,
      @RequestBody CreateReviewCommentRequestDto requestDto
  ) {
    final var now = LocalDateTime.now();
    final var userId = Long.parseLong(user.getUsername());
    final var response =
        createReviewCommentUseCase.createComment(requestDto.toCommand(userId, id), now);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateReviewCommentResponseDto(response.id()));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<CursorResponse<GetReviewCommentsResponseDto, Long>> getReviewComments(
      @PathVariable Long id,
      GetReviewCommentsRequestDto dto
  ) {
    final var comments = reviewDetailFacadeService.getReviewComments(dto.toCommand(id));
    final var responseItems =
        comments.getItems().stream().map(GetReviewCommentsResponseDto::from).toList();

    return ResponseEntity.ok(CursorResponse.of(
        responseItems,
        comments.hasNext(),
        comments.hasPrevious(),
        comments.getFirstCursor().id(),
        comments.getLastCursor().id()
    ));
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}/comments/{commentId}")
  public ResponseEntity<DeleteReviewCommentResponseDto> deleteReviewComment(
      @AuthenticationPrincipal User user,
      @PathVariable Long id,
      @PathVariable Long commentId
  ) {
    final var now = LocalDateTime.now();
    final var userId = Long.parseLong(user.getUsername());
    deleteReviewCommentUseCase.delete(commentId, userId, now);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new DeleteReviewCommentResponseDto(commentId));
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/{id}/comments/{commentId}")
  public ResponseEntity<Void> updateReviewComment(
      @AuthenticationPrincipal User user,
      @PathVariable Long id,
      @PathVariable Long commentId,
      @RequestBody String comment
  ) {
    final var userId = Long.parseLong(user.getUsername());
    updateReviewCommentUseCase.updateReviewComment(userId, commentId, comment);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
