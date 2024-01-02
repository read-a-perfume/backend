package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;
import java.util.List;

public interface CategoryRepository {
  Category save(Category category);

  void deleteUserTypes(Long userId);

  void saveUserTypes(Long userId, List<CategoryUser> categoryUser);
}
