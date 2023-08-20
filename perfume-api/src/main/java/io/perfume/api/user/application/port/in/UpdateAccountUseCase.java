package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;

public interface UpdateAccountUseCase {
  void updateUserEmail(UpdateEmailCommand updateEmailCommand);

  void updateUserPassword(UpdatePasswordCommand updatePasswordCommand);
}
