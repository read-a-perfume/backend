package io.perfume.api.mypage.adapter.port.in.http;

import io.perfume.api.mypage.adapter.port.in.http.dto.FollowCountResponseDto;
import io.perfume.api.mypage.adapter.port.in.http.dto.ReviewCountResponseDto;
import io.perfume.api.mypage.application.port.in.FollowUserUseCase;
import io.perfume.api.mypage.application.port.in.GetFollowCountUseCase;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.review.application.in.review.GetReviewCountUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mypage")
@PreAuthorize("isAuthenticated()")
public class MypageController {

  private final FollowUserUseCase followUserUseCase;

  private final GetFollowCountUseCase getFollowCountUseCase;

  private final GetReviewCountUseCase getReviewCountUseCase;

  private final FindCategoryUseCase findCategoryUseCase;

  public MypageController(
      FollowUserUseCase followUserUseCase,
      GetFollowCountUseCase getFollowCountUseCase,
      GetReviewCountUseCase getReviewCountUseCase,
      FindCategoryUseCase findCategoryUseCase) {
    this.followUserUseCase = followUserUseCase;
    this.getFollowCountUseCase = getFollowCountUseCase;
    this.getReviewCountUseCase = getReviewCountUseCase;
    this.findCategoryUseCase = findCategoryUseCase;
  }

  @PostMapping("/{id}/follow")
  @ResponseStatus(HttpStatus.CREATED)
  public void followUser(@AuthenticationPrincipal User user, @PathVariable("id") Long followingId) {
    var userId = Long.parseLong(user.getUsername());
    followUserUseCase.followAndUnFollow(userId, followingId);
  }

  @GetMapping("/{id}/follows")
  public ResponseEntity<FollowCountResponseDto> getFollowCount(@PathVariable("id") Long userId) {
    var followerCount = getFollowCountUseCase.getFollowerCountByUserId(userId);
    var followingCount = getFollowCountUseCase.getFollowingCountByUserId(userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new FollowCountResponseDto(followerCount, followingCount));
  }

  @GetMapping("/{id}/reviews")
  public ResponseEntity<ReviewCountResponseDto> getReviewCount(@PathVariable("id") Long userId) {
    var reviewCount = getReviewCountUseCase.getReviewCountByUserId(userId);

    return ResponseEntity.status(HttpStatus.OK).body(new ReviewCountResponseDto(reviewCount));
  }

  // TODO : 컨트롤러 test
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResult>> getCategories(@AuthenticationPrincipal User user) {
    var userId = Long.parseLong(user.getUsername());

    return ResponseEntity.status(HttpStatus.OK).body(findCategoryUseCase.findTasteByUserId(userId));
  }
}
