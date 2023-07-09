package io.perfume.api.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationKeyTest {

  @Test
  @DisplayName("인증키가 유효한 시간인 경우 false를 반환한다.")
  void testIsExpiredIfValid() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey =
        AuthenticationKey.createAuthenticationKey("code", "key", now);

    // when
    boolean result = authenticationKey.isExpired(now);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("인증키가 유효한 시간인 경우 true를 반환한다.")
  void testIsExpiredIfInvalid() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey = AuthenticationKey.createAuthenticationKey("code", "key",
        now.minusMinutes(AuthenticationKey.EXPIRED_MINUTES));

    // when
    boolean result = authenticationKey.isExpired(now);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("매칭키와 인증키가 동일한 경우 true를 반환한다.")
  void testMatchKey() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey =
        AuthenticationKey.createAuthenticationKey("code", "key", now);

    // when
    boolean result = authenticationKey.matchKey("code", "key", now);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("매칭키가 다른 경우 false를 반환한다.")
  void testMatchKeyWhenNotMatchKey() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey =
        AuthenticationKey.createAuthenticationKey("code", "key", now);

    // when
    boolean result = authenticationKey.matchKey("code", "yek", now);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("인증키가 다른 경우 false를 반환한다.")
  void testMatchKeyWhenNotMatchCode() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey =
        AuthenticationKey.createAuthenticationKey("code", "key", now);

    // when
    boolean result = authenticationKey.matchKey("edoc", "key", now);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("유효하지 않은 인증키인 경우 인증키와 매칭키가 동일해도 false를 반환한다.")
  void testMatchKeyWhenExpiredKey() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey = AuthenticationKey.createAuthenticationKey("code", "key",
        now.minusMinutes(AuthenticationKey.EXPIRED_MINUTES));

    // when
    boolean result = authenticationKey.matchKey("code", "key", now);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("이미 인증한 인증키인 경우 인증키와 매칭키가 동일해도 false를 반환한다.")
  void testMatchKeyWhenAlreadyVerified() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey = new AuthenticationKey(
        1L,
        "code",
        "key",
        now,
        now,
        now,
        null
    );

    // when
    boolean result = authenticationKey.matchKey("code", "key", now);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("이미 인증한 인증키인 경우 true를 반환한다.")
  void testIsVerifiedWhenAlreadyVerified() {
    // given
    LocalDateTime now = LocalDateTime.now();
    AuthenticationKey authenticationKey = new AuthenticationKey(
        1L,
        "code",
        "key",
        now,
        now,
        now,
        null
    );

    // when
    boolean result = authenticationKey.isVerified();

    // then
    assertTrue(result);
  }
}
