package io.perfume.api.file.application.service;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.domain.File;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

  private final FileRepository fileRepository;

  public FileResult uploadFile(
      final byte[] uploadFile, final long userId, final LocalDateTime now) {
    // TODO: AWS S3에 파일 업로드
    final File uploadedFile =
        fileRepository.save(File.createFile("https://picsum.photos/200/300", userId, now));

    return new FileResult(uploadedFile.getId(), uploadedFile.getUrl());
  }

  public List<FileResult> uploadFiles(
      final long userId, final List<byte[]> files, final LocalDateTime now) {
    return files.stream()
        .map(
            file -> {
              final File uploadedFile =
                  fileRepository.save(
                      File.createFile("https://picsum.photos/200/300", userId, now));
              return new FileResult(uploadedFile.getId(), uploadedFile.getUrl());
            })
        .toList();
  }
}
