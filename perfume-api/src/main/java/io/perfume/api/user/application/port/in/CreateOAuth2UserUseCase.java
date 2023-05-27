package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;

public interface CreateOAuth2UserUseCase {

    UserResult signUpGeneralUserByEmail(SignUpGeneralUserCommand command);
}
