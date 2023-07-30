package io.perfume.api.file.application.port.out;

import io.perfume.api.file.domain.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {

    Optional<File> save(File file);

    List<File> saveAll(List<File> files);
}
