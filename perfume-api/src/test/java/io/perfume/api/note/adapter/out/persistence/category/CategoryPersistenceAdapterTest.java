package io.perfume.api.note.adapter.out.persistence.category;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.note.adapter.out.persistence.categoryUser.CategoryUserMapper;
import io.perfume.api.note.domain.Category;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({CategoryPersistenceAdapter.class, CategoryMapper.class, CategoryUserMapper.class})
@DataJpaTest
@EnableJpaAuditing
class CategoryPersistenceAdapterTest {

  @Autowired
  private CategoryPersistenceAdapter categoryPersistenceAdapter;

  @Test
  void testCreateCategory() {
    // given
    LocalDateTime now = LocalDateTime.now();
    Category category = Category.create("프루티", "달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.", "#달달한 #과즙미", 1L, now);

    // when
    Category createdCategory = categoryPersistenceAdapter.save(category);

    // then
    assertThat(createdCategory.getId()).isNotNegative();
    assertThat(createdCategory.getName()).isEqualTo("프루티");
    assertThat(createdCategory.getDescription()).isEqualTo("달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.");
    assertThat(createdCategory.getTags()).isEqualTo("#달달한 #과즙미");
    assertThat(createdCategory.getThumbnailId()).isEqualTo(1L);
  }
}
