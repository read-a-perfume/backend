package io.perfume.api.mypage.adapter.port.in.http;

import io.perfume.api.mypage.adapter.port.in.http.dto.FollowCountResponseDto;
import io.perfume.api.mypage.application.port.in.FollowUserUseCase;
import io.perfume.api.mypage.application.port.in.GetFollowCountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mypage")
public class MypageController {

  private final FollowUserUseCase followUserUseCase;
  private final GetFollowCountUseCase getFollowCountUseCase;

  public MypageController(FollowUserUseCase followUserUseCase,
      GetFollowCountUseCase getFollowCountUseCase) {
    this.followUserUseCase = followUserUseCase;
    this.getFollowCountUseCase = getFollowCountUseCase;
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/{id}/follow")
  public void followUser(@AuthenticationPrincipal User user, @PathVariable("id") Long followingId) {
    var userId = Long.parseLong(user.getUsername());
    followUserUseCase.followAndUnFollow(userId, followingId);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping()
  public ResponseEntity<FollowCountResponseDto> getFollowCount(@AuthenticationPrincipal User user) {
    var userId = Long.parseLong(user.getUsername());
    var followerCount = getFollowCountUseCase.getFollowerCountByUserId(userId);
    var followingCount = getFollowCountUseCase.getFollowingCountByUserId(userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new FollowCountResponseDto(followerCount, followingCount));
  }
}
