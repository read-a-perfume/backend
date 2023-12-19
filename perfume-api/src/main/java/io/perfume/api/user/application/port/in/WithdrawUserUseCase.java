package io.perfume.api.user.application.port.in;

import java.time.LocalDateTime;

public interface WithdrawUserUseCase {

  void withdraw(long userId, LocalDateTime deletedAt);
}
