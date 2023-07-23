package io.perfume.api.user.application.port.in;

import io.perfume.api.user.domain.User;

// 독립적인 기능이라면 usecase를 별도로 분리?
public interface SetUserProfileUseCase {

    void setUserProfilePicture();
    void setNickName(User user, String name);
}
