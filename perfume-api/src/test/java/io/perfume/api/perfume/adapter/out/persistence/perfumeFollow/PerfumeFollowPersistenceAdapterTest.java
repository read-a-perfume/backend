package io.perfume.api.perfume.adapter.out.persistence.perfumeFollow;

import io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.mapper.PerfumeFollowMapper;
import io.perfume.api.perfume.domain.PerfumeFollow;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({PerfumeFollowPersistenceAdapter.class, PerfumeFollowMapper.class})
@DataJpaTest
class PerfumeFollowPersistenceAdapterTest {

  @Autowired
  private PerfumeFollowPersistenceAdapter repository;

  @Test
  @DisplayName("팔로우를 저장한다.")
  void testSaveFollow() {
    // given
    LocalDateTime now = LocalDateTime.now();
    var follow = PerfumeFollow.create(
        1L,
        1L,
        now
    );

    // when
    var followed = repository.save(follow);

    // then
    Assertions.assertThat(followed.getUserId()).isEqualTo(1L);
    Assertions.assertThat(followed.getPerfumeId()).isEqualTo(1L);
    Assertions.assertThat(followed.getId()).isEqualTo(1L);
  }
}