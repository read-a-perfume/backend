package io.perfume.api.mypage.application.port.out;

import io.perfume.api.mypage.domain.Follow;

public interface FollowRepository {

  Follow save(Follow follow);
}
