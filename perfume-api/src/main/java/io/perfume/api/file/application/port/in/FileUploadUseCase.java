package io.perfume.api.file.application.port.in;

import io.perfume.api.file.application.port.in.dto.MultiFileResponseDto;
import io.perfume.api.file.application.port.in.dto.SaveFileResult;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUseCase {

  SaveFileResult singleFileUpload(Long userId, MultipartFile file, LocalDateTime now);

  MultiFileResponseDto multiFileUpload(User user, List<MultipartFile> files, LocalDateTime now);
}
