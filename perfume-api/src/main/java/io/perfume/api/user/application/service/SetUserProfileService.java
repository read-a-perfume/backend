package io.perfume.api.user.application.service;

import io.perfume.api.user.application.exception.NotFoundUserException;
import io.perfume.api.user.application.port.in.SetUserProfileUseCase;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SetUserProfileService implements SetUserProfileUseCase {

    private final UserQueryRepository userQueryRepository;
    private final UserRepository userRepository;

    public SetUserProfileService(UserQueryRepository userQueryRepository, UserRepository userRepository) {
        this.userQueryRepository = userQueryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void setUserProfilePicture() {

    }

    // 닉네임 형식 지정?
    @Override
    public void setNickName(User user, String name) {
        if (user == null) {
            new NotFoundUserException();
        } else {
            user.rename(name);
            userRepository.save(user);
        }
    }
}
