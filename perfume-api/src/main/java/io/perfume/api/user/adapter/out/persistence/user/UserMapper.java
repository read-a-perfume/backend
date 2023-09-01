package io.perfume.api.user.adapter.out.persistence.user;

import io.perfume.api.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toUser(UserEntity userEntity) {
    if (userEntity == null) {
      return null;
    }
    return User.withId(
        userEntity.getId(),
        userEntity.getUsername(),
        userEntity.getEmail(),
        userEntity.getPassword(),
        null,
        userEntity.getRole(),
        userEntity.getBusinessId(),
        userEntity.getThumbnailId(),
        userEntity.getCreatedAt(),
        userEntity.getUpdatedAt(),
        userEntity.getDeletedAt());
  }

  public UserEntity toUserJpaEntity(User user) {
    if (user == null) {
      return null;
    }
    return UserEntity.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .role(user.getRole())
        .promotionConsent(user.isPromotionConsent())
        .marketingConsent(user.isMarketingConsent())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .deletedAt(user.getDeletedAt())
        .build();
  }
}
