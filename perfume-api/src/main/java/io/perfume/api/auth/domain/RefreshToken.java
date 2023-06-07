package io.perfume.api.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@RedisHash
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    private String accessToken;
    private LocalDateTime expiredTime;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public boolean canIssueAccessToken(LocalDateTime now, String accessToken) {
        return isExpired(now) && isMatch(accessToken);
    }
    private boolean isExpired(LocalDateTime now) {
        return now.isAfter(expiredTime);
    }
    private boolean isMatch(String accessToken) {
        if(accessToken == null || this.accessToken == null) {
            return false;
        }
        return Objects.equals(accessToken, this.accessToken);
    }
    public static RefreshToken Login(String accessToken, LocalDateTime expiredTime) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.accessToken = accessToken;
        refreshToken.expiredTime = expiredTime;
        return refreshToken;
    }
}