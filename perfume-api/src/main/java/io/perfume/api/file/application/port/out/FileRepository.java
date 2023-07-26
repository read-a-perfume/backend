package io.perfume.api.file.application.port.out;

import io.perfume.api.file.domain.File;

import java.util.Optional;

public interface FileRepository {

    Optional<File> save(File file);
}
