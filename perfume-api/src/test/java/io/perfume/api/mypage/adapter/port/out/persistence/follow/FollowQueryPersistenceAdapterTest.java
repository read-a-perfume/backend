package io.perfume.api.mypage.adapter.port.out.persistence.follow;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.mypage.adapter.port.out.persistence.follow.mapper.FollowMapper;
import io.perfume.api.mypage.domain.Follow;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({FollowQueryPersistenceAdapter.class, FollowMapper.class, TestQueryDSLConfiguration.class})
@DataJpaTest
class FollowQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private FollowQueryPersistenceAdapter queryRepository;

  @Autowired
  private FollowMapper followMapper;

  @Test
  void testFindByFollowerId() {
    // given
    var followerId = 0L;
    var followingId = 1L;
    var now = LocalDateTime.now();
    var follow = Follow.create(
        followerId,
        followingId,
        now
    );

    var entity = followMapper.toEntity(follow);
    entityManager.persist(entity);
    entityManager.flush();
    entityManager.clear();

    // when
    var find = queryRepository.findByFollowerId(followerId).orElseThrow();

    // then
    assertThat(find.getId()).isEqualTo(1L);
    assertThat(find.getFollowerId()).isEqualTo(0L);
    assertThat(find.getFollowingId()).isEqualTo(1L);
    assertThat(find.getDeletedAt()).isNull();
  }

  @Test
  @DisplayName("팔로워와 팔로잉 id로 조회")
  void testFindByFollowerIdAndFollowingId() {
    // given
    var followerId = 0L;
    var followingId = 1L;
    var now = LocalDateTime.now();
    var follow = Follow.create(
        followerId,
        followingId,
        now
    );

    var entity = followMapper.toEntity(follow);
    entityManager.persist(entity);
    entityManager.flush();
    entityManager.clear();

    // when
    var find = queryRepository
        .findByFollowerIdAndFollowingId(followerId, followingId).orElseThrow();

    // then
    assertThat(find.getId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("사용자 팔로잉  확인")
  void testFindFollowingCountByFollowerId() {
    long followCount = 10;

    // given
    var followerId = 0L;
    var followingId = 1L;
    var now = LocalDateTime.now();
    for (int i = 0; i < followCount; i++) {
      var follow = Follow.create(
          followerId,
          followingId,
          now
      );
      followingId++;

      var entity = followMapper.toEntity(follow);
      entityManager.persist(entity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    Long find = queryRepository.findFollowingCountByFollowerId(0L);

    // then
    assertThat(followCount).isEqualTo(10L);
    assertThat(followCount).isEqualTo(find);
  }

  @Test
  @DisplayName("사용자를 팔로우 확인")
  void testFindFollowerCountByFollowingId() {
    long followCount = 10;

    // given
    var followerId = 0L;
    var followingId = 1L;
    var now = LocalDateTime.now();
    for (int i = 0; i < followCount; i++) {
      var follow = Follow.create(
          followerId,
          followingId,
          now
      );
      followerId++;

      var entity = followMapper.toEntity(follow);
      entityManager.persist(entity);
    }
    entityManager.flush();
    entityManager.clear();

    // when
    Long find = queryRepository.findFollowerCountByFollowingId(1L);

    // then
    assertThat(followCount).isEqualTo(10L);
    assertThat(followCount).isEqualTo(find);
  }
}