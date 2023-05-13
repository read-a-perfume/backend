package io.perfume.api.auth.adapter.out.persistence.persistence;

import io.perfume.api.auth.domain.AuthenticationKey;

public interface AuthenticationKeyRepository {

    AuthenticationKey save(AuthenticationKey authenticationKey);
}
