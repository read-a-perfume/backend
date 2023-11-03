package io.perfume.api.mypage.application.service;

import io.perfume.api.mypage.application.port.in.GetFollowCountUseCase;
import io.perfume.api.mypage.application.port.out.FollowQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetFollowCountService implements GetFollowCountUseCase {

  private final FollowQueryRepository followQueryRepository;

  public GetFollowCountService(FollowQueryRepository followQueryRepository) {
    this.followQueryRepository = followQueryRepository;
  }

  @Override
  public Long getFollowingCountByUserId(Long userId) {
    return followQueryRepository.findFollowerCountByFollowingId(userId);
  }

  @Override
  public Long getFollowerCountByUserId(Long userId) {
    return followQueryRepository.findFollowingCountByFollowerId(userId);
  }
}
