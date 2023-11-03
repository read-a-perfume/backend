package io.perfume.api.mypage.application.port.in;

public interface FollowUserUseCase {

  void followAndunFollow(Long followerId, Long followingId);
}
