package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.AuthenticationKey;

public interface AuthenticationKeyRepository {

  AuthenticationKey save(AuthenticationKey authenticationKey);
}
