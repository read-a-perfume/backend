package io.perfume.api.user.domain;

import encryptor.OneWayEncryptor;
import io.perfume.api.base.BaseTimeDomain;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.application.exception.UserPasswordNotMatchException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;
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

  public void updateThumbnailId(Long thumbnailId) {
    this.thumbnailId = thumbnailId;
  }

  /**
   * 사용자의 회원탈퇴(soft delete)
   *
   * @param now 삭제 시간에 할당되는 값
   */
  public void softDelete(LocalDateTime now) {
    super.markDelete(now);
  }

  /**
   * 현재 user의 username 필드 앞에서 "username.length / 2"만큼 "*" 치환하여 반환한다.
   *
   * @return 암호화된 username
   */
  public String getEncryptedUsername() {
    int length = username.length();

    int halfLength = length / 2;

    return username.substring(0, halfLength) + "*".repeat(length - halfLength);
  }

  /**
   * 사용자가 암호를 잊어버리는 경우 사용된다. 현재 시간을 기준으로 해당 user의 password에 새로운 암호를 할당한다.
   *
   * @param now 랜덤값을 만들기 위한 씨앗
   * @param oneWayEncryptor 암호화 유틸 클래스
   */
  public void resetPasswordFromForgotten(LocalDateTime now, OneWayEncryptor oneWayEncryptor) {

    String milliseconds = String.valueOf(now.toInstant(ZoneOffset.UTC).toEpochMilli());
    String uuid = String.valueOf(UUID.randomUUID().toString());
    String hashedValue = String.valueOf(Objects.hash(milliseconds, uuid));

    String plainNewPassword =
        milliseconds.substring(0, 3) + uuid.substring(0, 5) + hashedValue.substring(0, 4);
    this.password = oneWayEncryptor.hash(plainNewPassword);
  }

  public void updateEmail(String email) {
    this.email = email;
  }

  public void updatePassword(
      PasswordEncoder passwordEncoder, String oldPassword, String newPassword) {
    if (!passwordEncoder.matches(oldPassword, this.password)) {
      throw new UserPasswordNotMatchException(this.id);
    }
    this.password = passwordEncoder.encode(newPassword);
  }

  public void updateProfile(String bio, LocalDate birthday, Sex sex) {
    if (bio != null) {
      this.bio = bio;
    }
    if (birthday != null) {
      this.birthday = birthday;
    }
    if (sex != null) {
      this.sex = sex;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return marketingConsent == user.marketingConsent
        && promotionConsent == user.promotionConsent
        && Objects.equals(id, user.id)
        && Objects.equals(username, user.username)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && role == user.role
        && Objects.equals(bio, user.bio)
        && Objects.equals(birthday, user.birthday)
        && sex == user.sex
        && Objects.equals(businessId, user.businessId)
        && Objects.equals(thumbnailId, user.thumbnailId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        username,
        email,
        password,
        role,
        bio,
        birthday,
        sex,
        marketingConsent,
        promotionConsent,
        businessId,
        thumbnailId);
  }
}
