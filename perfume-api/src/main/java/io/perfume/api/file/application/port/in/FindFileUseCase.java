package io.perfume.api.file.application.port.in;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.domain.File;
import java.util.List;
import java.util.Optional;

public interface FindFileUseCase {

  Optional<File> findFileById(Long id);

  List<FileResult> findFilesByIds(List<Long> ids);
}
