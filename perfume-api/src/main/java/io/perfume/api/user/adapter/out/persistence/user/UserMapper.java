package io.perfume.api.user.adapter.out.persistence.user;

import io.perfume.api.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toUser(UserJpaEntity userJpaEntity) {
    if (userJpaEntity == null) {
      return null;
    }
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
        userJpaEntity.getDeletedAt(),
        userJpaEntity.getBirth(),
        userJpaEntity.getIsOauth());
  }

  public UserJpaEntity toUserJpaEntity(User user) {
    if (user == null) {
      return null;
    }
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
        .birth(user.getBirth())
        .isOauth(user.getIsOauth())
        .build();
  }
}
