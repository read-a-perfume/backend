package io.perfume.api.file.application.port.in;

import io.perfume.api.file.application.port.in.dto.MultiFileResponseDto;
import io.perfume.api.file.application.port.in.dto.SaveFileResult;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

public interface FileUploadUseCase {

    SaveFileResult singleFileUpload(Long userId, MultipartFile file, LocalDateTime now);

    MultiFileResponseDto multiFileUpload(User user, List<MultipartFile> files, LocalDateTime now);
}
