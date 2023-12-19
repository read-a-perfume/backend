package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.SignUpSocialUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;

public interface CreateUserUseCase {

  UserResult createUser(SignUpGeneralUserCommand command);

  UserResult createSocialUser(SignUpSocialUserCommand command);
}
