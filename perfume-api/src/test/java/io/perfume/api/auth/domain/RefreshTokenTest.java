package io.perfume.api.auth.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

public class RefreshTokenTest {

    @Test
    public void 만료기간이_지나면_새로운_액세스_토큰을_발급_받을_수_없는가() {
        String accessToken = "tmp-token";
        LocalDateTime oldTime = LocalDateTime.now().minusSeconds(5);
        RefreshToken sut = RefreshToken.Login(accessToken, oldTime);

        boolean result = sut.canIssueAccessToken(LocalDateTime.now());

        assertFalse(result);
    }

    @Test
    public void 만료기간이_지나지_않았다면_새로운_액세스_토큰을_발급_받을_수_없는가() {
        String accessToken = "tmp-token";
        LocalDateTime oldTime = LocalDateTime.now().plusSeconds(40);
        RefreshToken sut = RefreshToken.Login(accessToken, oldTime);

        boolean result = sut.canIssueAccessToken(LocalDateTime.now());

        assertTrue(result);
    }
}
