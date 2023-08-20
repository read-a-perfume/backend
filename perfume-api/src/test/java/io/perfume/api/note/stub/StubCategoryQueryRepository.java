package io.perfume.api.note.stub;

import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import io.perfume.api.note.domain.Category;
import java.util.List;
import java.util.Optional;

public class StubCategoryQueryRepository implements CategoryQueryRepository {

  @Override
  public List<Category> find() {
    return null;
  }

  @Override
  public Optional<Category> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<Category> findCategoryUserByUserId(Long id) {
    return null;
  }
}
