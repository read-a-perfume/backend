package io.perfume.api.mypage.application.port.out;

import io.perfume.api.mypage.domain.Follow;
import java.util.Optional;

public interface FollowQueryRepository {

  Optional<Follow> findByFollowerId(Long followerId);

  Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

  Long findFollowingCountByFollowerId(Long followerId);

  Long findFollowerCountByFollowingId(Long followingId);
}
