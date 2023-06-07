package io.perfume.api.auth.domain;

import jakarta.persistence.Id;
import lombok.*;
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
}