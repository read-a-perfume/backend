package io.perfume.api.note.application.service;

import io.perfume.api.note.application.port.in.CreateCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.AddUserTasteCommand;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.CategoryUser;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryService implements CreateCategoryUseCase {

  private final CategoryRepository categoryRepository;

  private final CategoryQueryRepository categoryQueryRepository;

  public CreateCategoryService(
      CategoryRepository categoryRepository, CategoryQueryRepository categoryQueryRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryQueryRepository = categoryQueryRepository;
  }

  @Override
  public CategoryResult create(AddUserTasteCommand command, LocalDateTime now) {
    var category = categoryQueryRepository.findById(command.categoryId()).orElseThrow();
    categoryRepository.save(CategoryUser.create(command.userId(), category, now));

    return CategoryResult.from(category);
  }
}
