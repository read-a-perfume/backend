package io.perfume.api.note.application.port.out;

import io.perfume.api.note.domain.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryQueryRepository {

  List<Category> find();

  Optional<Category> findById(Long id);

  List<Category> findCategoryUserByUserId(Long id);
}
