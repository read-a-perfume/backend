package io.perfume.api.auth.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@RedisHash
@RequiredArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    private Long userId;
    private LocalDateTime expiredTime;
    private String accessToken;

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