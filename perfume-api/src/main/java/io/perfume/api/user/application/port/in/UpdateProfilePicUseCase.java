package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UpdateProfilePicResult;
import java.time.LocalDateTime;

public interface UpdateProfilePicUseCase {

  UpdateProfilePicResult update(Long userId, byte[] imageFileContent, LocalDateTime now);
}
