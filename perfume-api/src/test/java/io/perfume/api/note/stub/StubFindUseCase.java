package io.perfume.api.note.stub;

import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import java.util.List;

public class StubFindUseCase implements FindCategoryUseCase {

  @Override
  public List<CategoryResult> findCategories() {
    return null;
  }

  @Override
  public CategoryResult findCategoryById(Long id) {
    return null;
  }

  @Override
  public List<CategoryResult> findTypeByUserId(Long id) {
    return null;
  }
}
