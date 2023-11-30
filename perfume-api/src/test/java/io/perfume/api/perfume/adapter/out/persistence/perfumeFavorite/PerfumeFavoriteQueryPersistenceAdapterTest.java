package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.mapper.PerfumeFavoriteMapper;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({PerfumeFavoriteQueryPersistenceAdapter.class, PerfumeFavoriteMapper.class,
    TestQueryDSLConfiguration.class})
@DataJpaTest
class PerfumeFavoriteQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private PerfumeFavoriteQueryPersistenceAdapter queryRepository;

  @Autowired
  private PerfumeFavoriteMapper perfumeFavoriteMapper;

  @Autowired
  private PerfumeFavoriteJpaRepository jpaRepository;

  @Test
  void testFindByUserAndPerfume() {
    // given
    LocalDateTime now = LocalDateTime.now();
    var follow = PerfumeFavorite.create(
        1L,
        1L,
        now
    );

    PerfumeFavoriteJpaEntity entity = jpaRepository.save(perfumeFavoriteMapper.toEntity(follow));

    // when
    PerfumeFavorite followed = queryRepository.findByUserIdAndPerfumeId(
        entity.getId().getUserId(), entity.getId().getPerfumeId())
        .orElseThrow();

    // then
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
      var follow = PerfumeFavorite.create(
          userId,
          perfumeId++,
          now
      );
      jpaRepository.save(perfumeFavoriteMapper.toEntity(follow));
    }
    perfumeId = 1L;

    // when
    List<PerfumeFavorite> followedPerfumes = queryRepository.findFavoritePerfumesByUserId(userId);

    // then
    assertThat(followedPerfumes.size()).isEqualTo(end - start);
    assertThat(followedPerfumes).allMatch(
        perfumeFollow -> perfumeFollow.getUserId().equals(userId));
    for (PerfumeFavorite perfume : followedPerfumes) {
      assertThat(perfumeId).isEqualTo(perfume.getPerfumeId());
      perfumeId++;
    }
  }
}