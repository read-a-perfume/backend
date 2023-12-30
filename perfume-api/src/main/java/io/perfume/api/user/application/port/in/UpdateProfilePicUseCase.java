package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UpdateProfilePicResult;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfilePicUseCase {

  UpdateProfilePicResult update(Long userId, MultipartFile file, LocalDateTime now);
}
