package io.perfume.api.user.fixture;

import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestUserRepository implements UserRepository {
    private final static Map<Long, User> DATA_BASE = new HashMap<>();

    @Override
    public Optional<User> loadUser(long userId) {
        return Optional.ofNullable(DATA_BASE.get(userId));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return DATA_BASE.values().stream()
                .filter(it -> it.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> save(User user) {
        DATA_BASE.put(user.getId(), user);
        User savedUser = DATA_BASE.get(user.getId());
        return Optional.of(savedUser);
    }

}

