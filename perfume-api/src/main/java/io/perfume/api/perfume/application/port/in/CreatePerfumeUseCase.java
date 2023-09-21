package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;

public interface CreatePerfumeUseCase {
  void createPerfume(CreatePerfumeCommand createPerfumeCommand);
}
