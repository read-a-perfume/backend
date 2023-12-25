package io.perfume.api.user.application.port.out;

import java.time.Duration;

public interface EmailCodeRepository {
  void save(String email, String code, Duration duration);
}
