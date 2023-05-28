package jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonWebTokenGeneratorTest {

    @Test
    @DisplayName("JWT 생성한다.")
    void testCreateJWT() {
        // given
        JsonWebTokenGenerator jsonWebTokenGenerator = new JsonWebTokenGenerator("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        // when
        String jwt = jsonWebTokenGenerator.create(subject, claims, expirationSeconds, now);

        // then
        assertNotNull(jwt);
    }

    @Test
    @DisplayName("발급된 JWT이 만료되지 않은 경우 false를 반환한다.")
    void testIsExpired() {
        // given
        JsonWebTokenGenerator jsonWebTokenGenerator = new JsonWebTokenGenerator("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String jwt = jsonWebTokenGenerator.create(subject, claims, expirationSeconds, now);

        // when
        boolean expired = jsonWebTokenGenerator.isExpired(jwt, now.plusSeconds(expirationSeconds - 1));

        // then
        assertFalse(expired);
    }

    @Test
    @DisplayName("발급된 JWT이 만료된 경우 true를 반환한다.")
    void testIsExpiredWhenExpiated() {
        // given
        JsonWebTokenGenerator jsonWebTokenGenerator = new JsonWebTokenGenerator("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String jwt = jsonWebTokenGenerator.create(subject, claims, expirationSeconds, now);

        // when
        boolean expired = jsonWebTokenGenerator.isExpired(jwt, now.plusSeconds(expirationSeconds + 1));

        // then
        assertTrue(expired);
    }

    @Test
    @DisplayName("JWT에 포함된 Subject를 가져온다.")
    void testGetSubject() {
        // given
        JsonWebTokenGenerator jsonWebTokenGenerator = new JsonWebTokenGenerator("12341234123123123123123123123123");
        String subject = "subject";
        int expirationSeconds = 60 * 60 * 24 * 30;
        Map<String, Object> claims = Map.of("userId", "hi");
        LocalDateTime now = LocalDateTime.now();
        String jwt = jsonWebTokenGenerator.create(subject, claims, expirationSeconds, now);

        // when
        String extractSubject = jsonWebTokenGenerator.getSubject(jwt);

        // then
        assertEquals(extractSubject, subject);
    }
}
