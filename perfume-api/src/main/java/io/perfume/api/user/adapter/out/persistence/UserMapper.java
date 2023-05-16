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
                userJpaEntity.getThumbnailId(),
                userJpaEntity.getCreatedAt(),
                userJpaEntity.getUpdatedAt(),
                userJpaEntity.getDeletedAt());
    }

    public UserJpaEntity toUserJpaEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .promotionConsent(user.isPromotionConsent())
                .marketingConsent(user.isMarketingConsent())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }
}
