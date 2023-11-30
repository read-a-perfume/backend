package io.perfume.api.review.adapter.out.persistence.repository.tag;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewEntity;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewTagEntity;
import io.perfume.api.review.adapter.out.persistence.entity.TagEntity;
import io.perfume.api.review.adapter.out.persistence.repository.tag.ReviewTagMapper;
import io.perfume.api.review.adapter.out.persistence.repository.tag.TagMapper;
import io.perfume.api.review.adapter.out.persistence.repository.tag.TagQueryPersistenceAdapter;
import io.perfume.api.review.domain.Tag;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
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
@Import({TagQueryPersistenceAdapter.class, TagMapper.class, ReviewTagMapper.class,
    TestQueryDSLConfiguration.class})
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

  @Test
  @DisplayName("리뷰 태그를 조회한다.")
  void testFindReviewTags() {
    // given
    var now = LocalDateTime.now();
    var tag = new TagEntity(null, "test", now, now, null);
    entityManager.persist(tag);
    var review =
        new ReviewEntity(null, "", "", Strength.LIGHT, 0L, DayType.DAILY, 1L, 1L, Season.SPRING, now, now, null);
    entityManager.persist(
        review);
    entityManager.persist(new ReviewTagEntity(review.getId(), tag.getId(), now, now, null));

    // when
    var result = queryRepository.findReviewTags(1L);

    // then
    assertThat(result.size()).isEqualTo(1L);
  }
}
