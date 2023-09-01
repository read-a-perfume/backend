package io.perfume.api.user.application.port.in;

import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

// 독립적인 기능이라면 usecase를 별도로 분리?
public interface SetUserProfileUseCase {

  void setUserProfilePicture(String userId, MultipartFile image, LocalDateTime now);
}
