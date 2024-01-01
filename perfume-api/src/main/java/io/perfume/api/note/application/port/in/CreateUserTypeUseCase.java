package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.AddMyTypeCommand;
import java.time.LocalDateTime;

public interface CreateUserTypeUseCase {

  void create(AddMyTypeCommand command, LocalDateTime now);
}
