package io.perfume.api.mypage.application.port.in;

public interface GetFollowCountUseCase {

  Long getFollowingCountByUserId(Long userId);

  Long getFollowerCountByUserId(Long userId);
}
