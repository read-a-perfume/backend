package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.SignUpSocialUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.time.LocalDateTime;

public interface CreateUserUseCase {

  UserResult signUpGeneralUserByEmail(SignUpGeneralUserCommand command);

  UserResult signUpSocialUser(SignUpSocialUserCommand command, LocalDateTime now);
}
