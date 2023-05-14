package io.perfume.api.user.adapter.out.persistence;

import io.perfume.api.user.domain.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    public User toUser(UserJpaEntity userJpaEntity) {
        return User.withId(
                userJpaEntity.getId(),
                userJpaEntity.getUsername(),
                userJpaEntity.getEmail(),
                userJpaEntity.getPassword(),
                userJpaEntity.getName(),
                userJpaEntity.getRole(),
                userJpaEntity.getBusinessId(),
                userJpaEntity.getThumbnailId());
    }

    public UserJpaEntity toUserJpaEntity(User user) {
        return UserJpaEntity.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
