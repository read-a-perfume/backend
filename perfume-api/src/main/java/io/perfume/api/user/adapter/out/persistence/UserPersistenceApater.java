package io.perfume.api.user.adapter.out.persistence;

import io.perfume.api.sample.application.exception.EntityNotFoundException;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UserPersistenceApater implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User loadUser(long userId) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        return userMapper.toUser(userJpaEntity);
    }
}
