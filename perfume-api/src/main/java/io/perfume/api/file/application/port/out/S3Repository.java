package io.perfume.api.file.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface S3Repository {
  String uploadFile(MultipartFile file);
}
