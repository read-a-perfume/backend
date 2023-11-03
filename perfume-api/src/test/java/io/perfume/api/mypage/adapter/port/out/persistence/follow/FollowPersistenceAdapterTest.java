package io.perfume.api.mypage.adapter.port.out.persistence.follow;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.mypage.adapter.port.out.persistence.follow.mapper.FollowMapper;
import io.perfume.api.mypage.application.port.out.FollowRepository;
import io.perfume.api.mypage.domain.Follow;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({FollowPersistenceAdapter.class, FollowMapper.class})
@DataJpaTest
class FollowPersistenceAdapterTest {

  @Autowired
  FollowRepository followRepository;

  @Test
  @DisplayName("팔로우를 저장한다.")
  void testSaveFollow() {
    // given
    var followerId = 0L;
    var followingId = 1L;
    var now = LocalDateTime.now();
    var follow = Follow.create(
        followerId,
        followingId,
        now
    );

    // when
    var saved = followRepository.save(follow);

    // then
    assertThat(saved.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(saved.getId()).isEqualTo(1L);
    assertThat(saved.getFollowerId()).isEqualTo(0L);
    assertThat(saved.getFollowingId()).isEqualTo(1L);
    assertThat(saved.getDeletedAt()).isNull();
  }
}