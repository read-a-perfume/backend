package io.perfume.api.auth.domain;

import io.perfume.api.base.BaseTimeDomain;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AuthenticationKey extends BaseTimeDomain {

  public static final int EXPIRED_MINUTES = 3;

  private final Long id;

  private final String code;

  private final String key;

  private LocalDateTime verifiedAt;

  public AuthenticationKey(
      Long id,
      String code,
      String key,
      LocalDateTime verifiedAt,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.id = id;
    this.code = code;
    this.key = key;
    this.verifiedAt = verifiedAt;
  }

  public static AuthenticationKey createAuthenticationKey(
      @NotNull String code, @NotNull String key, @NotNull LocalDateTime now) {
    return new AuthenticationKey(null, code, key, null, now, now, null);
  }

  public boolean isExpired(LocalDateTime now) {
    LocalDateTime expiredAt = now.minusMinutes(EXPIRED_MINUTES);
    return getCreatedAt().isEqual(expiredAt) || getCreatedAt().isBefore(expiredAt);
  }

  public boolean matchKey(String code, String key, LocalDateTime now) {
    boolean isValid = !this.isVerified() && !this.isExpired(now);
    boolean isMatched = this.code.equals(code) && this.key.equals(key);

    if (isValid && isMatched) {
      this.verifiedAt = now;
      return true;
    }

    return false;
  }

  public boolean isVerified() {
    return verifiedAt != null;
  }
}
