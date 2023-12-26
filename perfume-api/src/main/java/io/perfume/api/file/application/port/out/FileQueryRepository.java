package io.perfume.api.file.application.port.out;

import io.perfume.api.file.domain.File;
import java.util.List;
import java.util.Optional;

public interface FileQueryRepository {

  Optional<File> findOneByFileId(Long fileId);

  Optional<File> findOneByFileURL(String fileURL);

  List<File> findByIds(List<Long> fileIds);
}
