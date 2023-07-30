package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.in.dto.AddUserTasteCommand;
import java.time.LocalDateTime;

public interface CreateCategoryUseCase {

  CategoryResult create(AddUserTasteCommand command, LocalDateTime now);
}
