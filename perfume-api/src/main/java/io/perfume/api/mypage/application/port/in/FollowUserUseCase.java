package io.perfume.api.mypage.application.port.in;

public interface FollowUserUseCase {

  void followAndUnFollow(Long followerId, Long followingId);
}
