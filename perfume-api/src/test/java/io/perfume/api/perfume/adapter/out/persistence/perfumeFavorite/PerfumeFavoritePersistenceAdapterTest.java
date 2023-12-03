package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.mapper.PerfumeFavoriteMapper;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({PerfumeFavoritePersistenceAdapter.class, PerfumeFavoriteMapper.class})
@DataJpaTest
class PerfumeFavoritePersistenceAdapterTest {

  @Autowired private PerfumeFavoritePersistenceAdapter repository;

  @Test
  @DisplayName("즐겨찾기 한다.")
  void testSaveFavorite() {
    // given
    LocalDateTime now = LocalDateTime.now();
    var follow = PerfumeFavorite.create(1L, 1L, now);

    // when
    var followed = repository.save(follow);

    // then
    assertThat(followed.getUserId()).isEqualTo(1L);
    assertThat(followed.getPerfumeId()).isEqualTo(1L);
  }
}
