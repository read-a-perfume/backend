package encryptor.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    @DisplayName("JWT 생성한다.")
    void testCreateJWT() {
        // given
        JwtUtil jwtUtil = new JwtUtil("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        // when
        String jwt = jwtUtil.create(subject, claims, expirationSeconds, now);

        // then
        assertNotNull(jwt);
    }

    @Test
    @DisplayName("발급된 JWT이 만료되지 않은 경우 false를 반환한다.")
    void testIsExpired() {
        // given
        JwtUtil jwtUtil = new JwtUtil("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String jwt = jwtUtil.create(subject, claims, expirationSeconds, now);

        // when
        boolean expired = jwtUtil.isExpired(jwt, now.plusSeconds(expirationSeconds - 1));

        // then
        assertFalse(expired);
    }

    @Test
    @DisplayName("발급된 JWT이 만료된 경우 true를 반환한다.")
    void testIsExpiredWhenExpiated() {
        // given
        JwtUtil jwtUtil = new JwtUtil("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String jwt = jwtUtil.create(subject, claims, expirationSeconds, now);

        // when
        boolean expired = jwtUtil.isExpired(jwt, now.plusSeconds(expirationSeconds + 1));

        // then
        assertTrue(expired);
    }

    @Test
    @DisplayName("JWT에 포함된 Subject를 가져온다.")
    void testGetSubject() {
        // given
        JwtUtil jwtUtil = new JwtUtil("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = Map.of("userId", "hi");
        LocalDateTime now = LocalDateTime.now();
        String jwt = jwtUtil.create(subject, claims, expirationSeconds, now);

        // when
        String extractSubject = jwtUtil.getSubject(jwt);

        // then
        assertEquals(extractSubject, subject);
    }
}
