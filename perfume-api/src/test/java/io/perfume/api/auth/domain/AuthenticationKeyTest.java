package io.perfume.api.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationKeyTest {

    @Test
    @DisplayName("인증키가 유효한 시간인 경우 false를 반환한다.")
    void testIsExpiredIfValid() {
        // given
        LocalDateTime now = LocalDateTime.now();
        AuthenticationKey authenticationKey = AuthenticationKey.createAuthenticationKey(1L, "code", "key", now);

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
        AuthenticationKey authenticationKey = AuthenticationKey.createAuthenticationKey(1L, "code", "key", now.minusMinutes(AuthenticationKey.EXPIRED_MINUTES));

        // when
        boolean result = authenticationKey.isExpired(now);

        // then
        assertTrue(result);
    }

    @Test
    void matchKey() {
    }
}
