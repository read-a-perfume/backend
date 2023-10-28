package io.perfume.api.perfume.adapter.out.persistence.perfumeFollow;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.mapper.PerfumeFollowMapper;
import io.perfume.api.perfume.domain.PerfumeFollow;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({PerfumeFollowQueryPersistenceAdapter.class, PerfumeFollowMapper.class,
    TestQueryDSLConfiguration.class})
@DataJpaTest
class PerfumeFollowQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private PerfumeFollowQueryPersistenceAdapter queryRepository;

  @Autowired
  private PerfumeFollowMapper perfumeFollowMapper;

  @Autowired
  private PerfumeFollowJpaRepository jpaRepository;

  @Test
  void testFindByUserAndPerfume() {
    // given
    LocalDateTime now = LocalDateTime.now();
    var follow = PerfumeFollow.create(
        1L,
        1L,
        now
    );

    PerfumeFollowJpaEntity entity = jpaRepository.save(perfumeFollowMapper.toEntity(follow));
    entityManager.clear();

    // when
    PerfumeFollow followed = queryRepository.findByUserAndPerfume(entity.getUserId(),
        entity.getPerfumeId()).orElseThrow();

    // then
    assertThat(followed.getId()).isEqualTo(1L);
    assertThat(followed.getUserId()).isEqualTo(1L);
    assertThat(followed.getPerfumeId()).isEqualTo(1L);
    assertThat(followed.getUserId()).isEqualTo(follow.getUserId());
    assertThat(followed.getPerfumeId()).isEqualTo(follow.getPerfumeId());
  }

  @Test
  void testFindFollowedPerfumesByUser() {
    // given
    int start = 0;
    int end = 10;
    LocalDateTime now = LocalDateTime.now();
    Long userId = 1L;
    Long perfumeId = 1L;

    for (int i = start; i < end; i++) {
      var follow = PerfumeFollow.create(
          userId,
          perfumeId++,
          now
      );
      jpaRepository.save(perfumeFollowMapper.toEntity(follow));
    }
    perfumeId = 1L;
    entityManager.clear();

    // when
    List<PerfumeFollow> followedPerfumes = queryRepository.findFollowedPerfumesByUser(userId);

    // then
    assertThat(followedPerfumes.size()).isEqualTo(end - start);
    assertThat(followedPerfumes).allMatch(
        perfumeFollow -> perfumeFollow.getUserId().equals(userId));
    for (PerfumeFollow perfume : followedPerfumes) {
      assertThat(perfumeId).isEqualTo(perfume.getPerfumeId());
      perfumeId++;
    }
  }
}