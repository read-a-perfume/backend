package io.perfume.api.note.application.service;

import io.perfume.api.note.application.exception.CategoryNotFoundException;
import io.perfume.api.note.application.port.in.CreateUserTypeUseCase;
import io.perfume.api.note.application.port.in.dto.AddMyTypeCommand;
import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserTypeService implements CreateUserTypeUseCase {

  private final CategoryRepository categoryRepository;

  private final CategoryQueryRepository categoryQueryRepository;

  @Override
  @Transactional
  public void create(AddMyTypeCommand command, LocalDateTime now) {
    if (command.categoryIds().isEmpty()) {
      categoryRepository.deleteUserTypes(command.userId());
      return;
    }

    List<Category> categories = categoryQueryRepository.findByIds(command.categoryIds());
    if (categories.size() != command.categoryIds().size()) {
      throw new CategoryNotFoundException();
    }

    List<CategoryUser> categoryUsers =
        categories.stream()
            .map(category -> CategoryUser.create(command.userId(), category, now))
            .toList();

    categoryRepository.saveUserTypes(command.userId(), categoryUsers);
  }
}
