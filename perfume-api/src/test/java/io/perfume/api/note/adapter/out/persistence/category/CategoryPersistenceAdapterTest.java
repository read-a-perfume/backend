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
    Category category = Category.create("test", "test description", 1L, now);

    // when
    Category createdCategory = categoryPersistenceAdapter.save(category);

    // then
    assertThat(createdCategory.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdCategory.getName()).isEqualTo("test");
    assertThat(createdCategory.getDescription()).isEqualTo("test description");
    assertThat(createdCategory.getThumbnailId()).isEqualTo(1L);
  }
}
