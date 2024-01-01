package io.perfume.api.note.application.service;

import io.perfume.api.note.application.exception.CategoryNotFoundException;
import io.perfume.api.note.application.port.in.CreateUserTypeUseCase;
import io.perfume.api.note.application.port.in.dto.AddMyTypeCommand;
import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.CategoryUser;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserTypeService implements CreateUserTypeUseCase {

  private final CategoryRepository categoryRepository;

  private final CategoryQueryRepository categoryQueryRepository;

  @Override
  public void create(AddMyTypeCommand command, LocalDateTime now) {
    var category =
        categoryQueryRepository
            .findById(command.categoryId())
            .orElseThrow(() -> new CategoryNotFoundException(command.categoryId()));
    categoryRepository.save(CategoryUser.create(command.userId(), category, now));
  }
}
