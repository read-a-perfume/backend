package io.perfume.api.note.application.service;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import io.perfume.api.note.application.exception.NotFoundNoteException;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCategoryService implements FindCategoryUseCase {

  private final CategoryQueryRepository categoryQueryRepository;
  private final FindFileUseCase findFileUseCase;

  @Override
  public List<CategoryResult> findCategories() {
    return categoryQueryRepository.find().stream()
        .map(
            category -> {
              String thumbnail =
                  findFileUseCase
                      .findFileById(category.getThumbnailId())
                      .orElse(File.createFile("", null, null))
                      .getUrl();
              return CategoryResult.from(category, thumbnail);
            })
        .toList();
  }

  @Override
  public CategoryResult findCategoryById(Long id) {
    return categoryQueryRepository
        .findById(id)
        .map(
            category -> {
              String thumbnail =
                  findFileUseCase
                      .findFileById(category.getThumbnailId())
                      .orElse(File.createFile("", null, null))
                      .getUrl();
              return CategoryResult.from(category, thumbnail);
            })
        .orElseThrow(() -> new NotFoundNoteException(id));
  }

  @Override
  public List<CategoryResult> findTypeByUserId(Long id) {
    return categoryQueryRepository.findCategoryUserByUserId(id).stream()
        .map(
            category -> {
              String thumbnail =
                  findFileUseCase
                      .findFileById(category.getThumbnailId())
                      .orElse(File.createFile("", null, null))
                      .getUrl();
              return CategoryResult.from(category, thumbnail);
            })
        .toList();
  }
}
