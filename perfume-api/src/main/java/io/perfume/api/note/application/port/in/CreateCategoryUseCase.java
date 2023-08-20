package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.AddUserTasteCommand;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import java.time.LocalDateTime;

public interface CreateCategoryUseCase {

  CategoryResult create(AddUserTasteCommand command, LocalDateTime now);
}
