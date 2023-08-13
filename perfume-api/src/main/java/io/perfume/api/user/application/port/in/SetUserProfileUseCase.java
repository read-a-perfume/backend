package io.perfume.api.user.application.port.in;

import io.perfume.api.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

// 독립적인 기능이라면 usecase를 별도로 분리?
public interface SetUserProfileUseCase {

    void setUserProfilePicture(String userId, MultipartFile image, LocalDateTime now);
    void setNickName(String userId, String name);
}
