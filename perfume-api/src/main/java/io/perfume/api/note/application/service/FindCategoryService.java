package io.perfume.api.note.application.service;

import io.perfume.api.note.application.exception.NotFoundNoteException;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FindCategoryService implements FindCategoryUseCase {

  private final CategoryQueryRepository categoryQueryRepository;

  public FindCategoryService(CategoryQueryRepository categoryQueryRepository) {
    this.categoryQueryRepository = categoryQueryRepository;
  }

  @Override
  public List<CategoryResult> findCategories() {
    return categoryQueryRepository.find().stream().map(CategoryResult::from).toList();
  }

  @Override
  public CategoryResult findCategoryById(Long id) {
    return categoryQueryRepository
        .findById(id)
        .map(CategoryResult::from)
        .orElseThrow(() -> new NotFoundNoteException(id));
  }

  @Override
  public List<CategoryResult> findTasteByUserId(Long id) {
    return categoryQueryRepository.findCategoryUserByUserId(id).stream()
        .map(CategoryResult::from)
        .toList();
  }
}
