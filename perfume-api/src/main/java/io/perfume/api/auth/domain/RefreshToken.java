package io.perfume.api.auth.domain;

import io.perfume.api.auth.adapter.out.redis.RedisRefreshToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    private UUID tokenId;
    private Long userId;

    public RefreshToken(Long userId) {
        this.tokenId = UUID.randomUUID();
        this.userId = userId;
    }

    @Builder
    private RefreshToken(UUID tokenId, Long userId) {
        this.tokenId = tokenId;
        this.userId = userId;
    }

    public static RefreshToken fromRedisRefreshToken(RedisRefreshToken refreshToken) {
        return RefreshToken.builder()
                .tokenId(refreshToken.getTokenId())
                .userId(refreshToken.getUserId())
                .build();
    }

    public static RefreshToken create(UUID tokenId, Long userId) {
        return new RefreshToken(tokenId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(tokenId, that.tokenId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId, userId);
    }
}
