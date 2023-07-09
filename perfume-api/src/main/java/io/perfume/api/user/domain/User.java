package io.perfume.api.user.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User extends BaseTimeDomain {

  private Long id;
  private String username;
  private String email;
  private String password;
  private String name;
  private Role role;
  private boolean marketingConsent;
  private boolean promotionConsent;
  private Long businessId;
  private Long thumbnailId;


  @Builder
  private User(Long id, String username, String email, String password,
               String name, Role role, Long businessId,
               Long thumbnailId, LocalDateTime createdAt, LocalDateTime updatedAt,
               LocalDateTime deletedAt, boolean marketingConsent, boolean promotionConsent) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
    this.businessId = businessId;
    this.thumbnailId = thumbnailId;
    this.marketingConsent = marketingConsent;
    this.promotionConsent = promotionConsent;
  }

  // 기업 사용자가 아닌 경우 회원가입시에 사용됩니다.
  public static User generalUserJoin(String username, String email, String password, String name,
                                     boolean marketingConsent, boolean promotionConsent) {
    return User.builder()
        .username(username)
        .email(email)
        .password(password)
        .name(name)
        .role(Role.USER)
        .marketingConsent(marketingConsent)
        .promotionConsent(promotionConsent)
        .build();
  }

  // Only Adapter
  public static User withId(
      Long id, String username, String email,
      String password, String name, Role role,
      Long businessId, Long thumbnailId, LocalDateTime createdAt, LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    return User.builder()
        .id(id)
        .username(username)
        .email(email)
        .password(password)
        .name(name)
        .role(role)
        .businessId(businessId)
        .thumbnailId(thumbnailId)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .deletedAt(deletedAt)
        .build();
  }
}
