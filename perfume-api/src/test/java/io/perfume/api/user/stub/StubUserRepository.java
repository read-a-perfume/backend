package io.perfume.api.user.stub;

import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;

import java.util.Optional;

public class StubUserRepository implements UserRepository, UserQueryRepository {

    @Override
    public Optional<User> loadUser(long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return Optional.empty();
    }
}
