package io.perfume.api.user.domain;

import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.application.service.dto.UpdateUserProfileCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class User extends BaseTimeDomain {

  private Long id;
  private String username;
  private String email;
  private String password;
  private Role role;
  private String bio;
  private LocalDate birthday;
  private Sex sex;
  private boolean marketingConsent;
  private boolean promotionConsent;
  private Long businessId;
  private Long thumbnailId;

  @Builder
  private User(
      Long id,
      String username,
      String email,
      String password,
      Role role,
      String bio,
      LocalDate birthday,
      Sex sex,
      Long businessId,
      Long thumbnailId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt,
      boolean marketingConsent,
      boolean promotionConsent) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.bio = bio;
    this.birthday = birthday;
    this.sex = sex;
    this.businessId = businessId;
    this.thumbnailId = thumbnailId;
    this.marketingConsent = marketingConsent;
    this.promotionConsent = promotionConsent;
  }

  // 기업 사용자가 아닌 경우 회원가입시에 사용됩니다.
  public static User generalUserJoin(
      String username,
      String email,
      String password,
      boolean marketingConsent,
      boolean promotionConsent) {
    return User.builder()
        .username(username)
        .email(email)
        .password(password)
        .role(Role.USER)
        .sex(Sex.OTHER)
        .marketingConsent(marketingConsent)
        .promotionConsent(promotionConsent)
        .build();
  }

  public static User createSocialUser(
      String username, String email, String password, LocalDateTime now) {
    return User.builder()
        .username(username)
        .email(email)
        .password(password)
        .role(Role.USER)
        .sex(Sex.OTHER)
        .marketingConsent(false)
        .promotionConsent(false)
        .createdAt(now)
        .updatedAt(now)
        .deletedAt(null)
        .build();
  }

  // Only Adapter
  public static User withId(
      Long id,
      String username,
      String email,
      String password,
      Role role,
      String bio,
      LocalDate birthday,
      Sex sex,
      Long businessId,
      Long thumbnailId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    return User.builder()
        .id(id)
        .username(username)
        .email(email)
        .password(password)
        .role(role)
        .bio(bio)
        .birthday(birthday)
        .sex(sex)
        .marketingConsent(false)
        .promotionConsent(false)
        .businessId(businessId)
        .thumbnailId(thumbnailId)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .deletedAt(deletedAt)
        .build();
  }

  public void updatePassword(final String plainPassword, final PasswordEncoder encoder) {
    this.password = encoder.encode(plainPassword);
  }

  public void updateProfile(final UpdateUserProfileCommand command) {
    this.bio = Objects.requireNonNullElse(command.bio(), this.bio);
    this.birthday = Objects.requireNonNullElse(command.birthday(), this.birthday);
    this.sex = Objects.requireNonNullElse(command.sex(), this.sex);
    this.thumbnailId = Objects.requireNonNullElse(command.thumbnailId(), this.thumbnailId);
    this.email = Objects.requireNonNullElse(command.email(), this.email);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public boolean verifyPassword(final String password, final PasswordEncoder encoder) {
    return encoder.matches(password, this.password);
  }
}
