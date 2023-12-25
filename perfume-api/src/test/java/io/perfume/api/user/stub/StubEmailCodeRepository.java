package io.perfume.api.user.stub;

import io.perfume.api.user.application.port.out.EmailCodeRepository;
import java.time.Duration;

public class StubEmailCodeRepository implements EmailCodeRepository {
  @Override
  public void save(String email, String code, Duration duration) {
    // do nothing
  }
}
