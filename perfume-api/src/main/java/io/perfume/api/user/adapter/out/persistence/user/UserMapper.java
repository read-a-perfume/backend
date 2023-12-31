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
        userEntity.getRole(),
        userEntity.getBio(),
        userEntity.getBirthday(),
        userEntity.getSex(),
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
        .thumbnailId(user.getThumbnailId())
        .role(user.getRole())
        .bio(user.getBio())
        .birthday(user.getBirthday())
        .sex(user.getSex())
        .promotionConsent(user.isPromotionConsent())
        .marketingConsent(user.isMarketingConsent())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .deletedAt(user.getDeletedAt())
        .build();
  }
}
