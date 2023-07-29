package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;

public interface CategoryRepository {
  Category save(Category category);

  CategoryUser save(CategoryUser categoryUser);
}
