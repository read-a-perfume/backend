package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;
import io.perfume.api.user.application.port.in.dto.UpdateProfileCommand;

public interface UpdateAccountUseCase {
  long updateUserEmail(UpdateEmailCommand updateEmailCommand);

  long updateUserPassword(UpdatePasswordCommand updatePasswordCommand);

  long updateUserProfile(UpdateProfileCommand updateProfileCommand);
}
