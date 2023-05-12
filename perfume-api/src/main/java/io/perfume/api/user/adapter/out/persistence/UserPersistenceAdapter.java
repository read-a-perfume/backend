package io.perfume.api.user.adapter.out.persistence;

import io.perfume.api.user.application.exception.EntityNotFoundException;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> loadUser(long userId) {
        return Optional.of(userMapper.toUser(userJpaRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new)));
    }
}
