package io.perfume.api.file.application.port.in;

import io.perfume.api.file.application.port.in.dto.FileResult;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUseCase {
  FileResult uploadFile(MultipartFile file, final long userId, final LocalDateTime now);

  List<FileResult> uploadFiles(
      final List<MultipartFile> files, final long userId, final LocalDateTime now);
}
