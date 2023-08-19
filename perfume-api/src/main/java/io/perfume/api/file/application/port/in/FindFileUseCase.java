package io.perfume.api.file.application.port.in;

import io.perfume.api.file.domain.File;

import java.util.Optional;

public interface FindFileUseCase {
    Optional<File> findFileById(Long id);
}
