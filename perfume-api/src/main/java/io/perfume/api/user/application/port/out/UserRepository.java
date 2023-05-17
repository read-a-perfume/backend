package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> loadUser(long userId);

    Optional<User> findByUsername(String username);

    Optional<User> save(User user);
}
