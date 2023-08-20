package io.perfume.api.review.adapter.out.persistence.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.review.adapter.out.persistence.mapper.TagMapper;
import io.perfume.api.review.domain.Tag;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({TagQueryPersistenceAdapter.class, TagMapper.class, TestQueryDSLConfiguration.class})
@DataJpaTest
@EnableJpaAuditing
class TagQueryPersistenceAdapterTest {

  @Autowired
  private TagMapper tagMapper;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private TagQueryPersistenceAdapter queryRepository;

  @Test
  @DisplayName("태그 id를 조회한다.")
  void testFindTags() {
    // given
    var now = LocalDateTime.now();
    var entity = tagMapper.toEntity(Tag.create("test", now));
    entityManager.persist(entity);
    var ids = List.of(entity.getId());

    // when
    var result = queryRepository.findByIds(ids);

    // then
    assertThat(result.size()).isEqualTo(1L);
    assertThat(result.get(0).getId()).isEqualTo(entity.getId());
  }

  @Test
  @DisplayName("빈 id 목록을 조회한 경우 빈 배열을 반환한다.")
  void testFindEmptyTags() {
    // given
    var ids = new ArrayList<Long>();

    // when
    var result = queryRepository.findByIds(ids);

    // then
    assertThat(result.size()).isEqualTo(0);
  }
}
