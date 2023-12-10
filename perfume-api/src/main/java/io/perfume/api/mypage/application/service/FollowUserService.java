package io.perfume.api.mypage.application.service;

import io.perfume.api.mypage.application.exception.FollowNotFoundException;
import io.perfume.api.mypage.application.exception.NotPermittedFollowUserException;
import io.perfume.api.mypage.application.port.in.FollowUserUseCase;
import io.perfume.api.mypage.application.port.out.FollowQueryRepository;
import io.perfume.api.mypage.application.port.out.FollowRepository;
import io.perfume.api.mypage.domain.Follow;
import io.perfume.api.user.application.exception.UserNotFoundException;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FollowUserService implements FollowUserUseCase {

  private final FollowRepository followRepository;

  private final FollowQueryRepository followQueryRepository;

  private final UserQueryRepository userQueryRepository;

  public FollowUserService(
      FollowRepository followRepository,
      FollowQueryRepository followQueryRepository,
      UserQueryRepository userQueryRepository) {
    this.followRepository = followRepository;
    this.followQueryRepository = followQueryRepository;
    this.userQueryRepository = userQueryRepository;
  }

  @Override
  public void followAndUnFollow(Long followerId, Long followingId) {
    User follwingUser =
        userQueryRepository
            .loadUser(followingId)
            .orElseThrow(() -> new UserNotFoundException(followingId));

    Optional<Follow> follow =
        followQueryRepository.findByFollowerIdAndFollowingId(followerId, follwingUser.getId());

    if (follow.isPresent()) {
      unFollow(followerId, follwingUser.getId());
      return;
    }

    follow(followerId, follwingUser.getId());
  }

  private void follow(Long followerId, Long followingId) {
    if (Objects.equals(followerId, followingId)) {
      throw new NotPermittedFollowUserException();
    }

    LocalDateTime now = LocalDateTime.now();
    Follow followed = Follow.create(followerId, followingId, now);
    followRepository.save(followed);
  }

  private void unFollow(Long followerId, Long followingId) {
    Follow follow =
        followQueryRepository
            .findByFollowerIdAndFollowingId(followerId, followingId)
            .orElseThrow(FollowNotFoundException::new);

    LocalDateTime now = LocalDateTime.now();
    follow.markDelete(now);
    followRepository.save(follow);
  }
}
