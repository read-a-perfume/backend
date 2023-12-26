package io.perfume.api.user.application.port.in;

import java.time.LocalDateTime;

import io.perfume.api.user.application.port.in.dto.UpdateProfilePicResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfilePicUseCase {

  UpdateProfilePicResult update(Long userId, byte[] imageFileContent, LocalDateTime now);
}
