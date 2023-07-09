package io.perfume.api.auth.stub;

import io.perfume.api.auth.application.port.out.AuthenticationKeyQueryRepository;
import io.perfume.api.auth.domain.AuthenticationKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubAuthenticationKeyQueryRepository implements AuthenticationKeyQueryRepository {

  private final List<AuthenticationKey> store = new ArrayList<>();

  @Override
  public Optional<AuthenticationKey> findByKey(String key) {
    return store.stream().filter(authenticationKey -> authenticationKey.getKey().equals(key))
        .findFirst();
  }

  public void add(AuthenticationKey authenticationKey) {
    store.add(authenticationKey);
  }

  public void clear() {
    store.clear();
  }
}
