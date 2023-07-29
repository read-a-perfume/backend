package io.perfume.api.note.application.service;

import io.perfume.api.note.application.port.in.CreateCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.in.dto.AddUserTasteCommand;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryService implements CreateCategoryUseCase {

  @Override
  public CategoryResult create(AddUserTasteCommand command, LocalDateTime now) {
    return null;
  }
}
