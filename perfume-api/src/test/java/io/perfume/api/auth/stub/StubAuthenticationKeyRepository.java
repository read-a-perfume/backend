package io.perfume.api.auth.stub;

import io.perfume.api.auth.application.port.out.AuthenticationKeyRepository;
import io.perfume.api.auth.domain.AuthenticationKey;

import java.util.ArrayList;
import java.util.List;

public class StubAuthenticationKeyRepository implements AuthenticationKeyRepository {

    private final List<AuthenticationKey> store = new ArrayList<>();

    @Override
    public AuthenticationKey save(AuthenticationKey authenticationKey) {
        store.add(authenticationKey);
        return authenticationKey;
    }

    public void clear() {
        store.clear();
    }

    public AuthenticationKey pop() {
        return store.remove(0);
    }
}
