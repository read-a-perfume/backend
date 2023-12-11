package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.application.port.in.dto.CreateMagazineCommand;
import io.perfume.api.brand.application.port.in.dto.MagazineResult;

public interface CreateMagazineUseCase {

  MagazineResult create(long userId, CreateMagazineCommand createMagazineCommand);
}
