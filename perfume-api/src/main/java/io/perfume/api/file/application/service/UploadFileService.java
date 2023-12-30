package io.perfume.api.file.application.service;

import io.perfume.api.file.application.port.in.UploadFileUseCase;
import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.application.port.out.S3Repository;
import io.perfume.api.file.domain.File;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

  private final FileRepository fileRepository;
  private final S3Repository s3Repository;

  @Transactional
  public FileResult uploadFile(MultipartFile file, long userId, LocalDateTime now) {
    String url = s3Repository.uploadFile(file);

    final File uploadedFile = fileRepository.save(File.createFile(url, userId, now));

    return new FileResult(uploadedFile.getId(), uploadedFile.getUrl());
  }

  public List<FileResult> uploadFiles(List<MultipartFile> files, long userId, LocalDateTime now) {
    return files.parallelStream()
        .map(
            file -> {
              String url = s3Repository.uploadFile(file);
              final File uploadedFile = fileRepository.save(File.createFile(url, userId, now));
              return new FileResult(uploadedFile.getId(), uploadedFile.getUrl());
            })
        .toList();
  }
}
