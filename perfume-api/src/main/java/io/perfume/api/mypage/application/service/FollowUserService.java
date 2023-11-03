package io.perfume.api.mypage.application.service;

import io.perfume.api.mypage.application.exception.FollowNotFoundException;
import io.perfume.api.mypage.application.port.in.FollowUserUseCase;
import io.perfume.api.mypage.application.port.out.FollowQueryRepository;
import io.perfume.api.mypage.application.port.out.FollowRepository;
import io.perfume.api.mypage.domain.Follow;
import java.time.LocalDateTime;
import java.util.Optional;

public class FollowUserService implements FollowUserUseCase {

  private final FollowRepository followRepository;

  private final FollowQueryRepository followQueryRepository;

  public FollowUserService(FollowRepository followRepository,
      FollowQueryRepository followQueryRepository) {
    this.followRepository = followRepository;
    this.followQueryRepository = followQueryRepository;
  }

  @Override
  public void followAndunFollow(Long followerId, Long followingId) {
    Optional<Follow> follow =
        followQueryRepository.findByFollowerIdAndFollowingId(followerId, followingId);

    if (follow.isPresent()) {
      unFollow(followerId, followingId);
      return;
    }
    follow(followerId, followingId);
  }

  private void follow(Long followerId, Long followingId) {
    LocalDateTime now = LocalDateTime.now();
    Follow followed = Follow.create(followerId, followingId, now);
    followRepository.save(followed);
  }

  private void unFollow(Long followerId, Long followingId) {
    Follow follow = followQueryRepository.findByFollowerIdAndFollowingId(followerId, followingId)
        .orElseThrow(() -> new FollowNotFoundException());

    LocalDateTime now = LocalDateTime.now();
    follow.markDelete(now);
    followRepository.save(follow);
  }
}
