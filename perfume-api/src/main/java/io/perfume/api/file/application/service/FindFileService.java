package io.perfume.api.file.application.service;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import io.perfume.api.file.domain.File;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindFileService implements FindFileUseCase {

  private final FileQueryRepository fileQueryRepository;

  @Override
  public Optional<File> findFileById(Long id) {
    if (id == null) {
      return Optional.empty();
    }
    return fileQueryRepository.findOneByFileId(id);
  }

  @Override
  public List<FileResult> findFilesByIds(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return List.of();
    }

    return fileQueryRepository.findByIds(ids).stream().map(FileResult::from).toList();
  }
}
