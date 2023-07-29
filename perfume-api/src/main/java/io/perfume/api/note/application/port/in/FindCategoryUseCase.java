package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.CategoryResult;
import java.util.List;

public interface FindCategoryUseCase {

  List<CategoryResult> findCategories();

  CategoryResult findCategoryById(Long id);

  List<CategoryResult> findTasteByUserId(Long id);
}
