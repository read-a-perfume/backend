package io.perfume.api.auth.domain;

import io.perfume.api.base.BaseTimeDomain;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthenticationKey extends BaseTimeDomain {

    private static final int EXPIRED_MINUTES = 3;

    private final Long id;

    private final Long userId;

    private final String key;

    private LocalDateTime verifiedAt;

    public AuthenticationKey(Long id, Long userId, String key, LocalDateTime verifiedAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);

        this.id = id;
        this.userId = userId;
        this.key = key;
        this.verifiedAt = verifiedAt;
    }

    static AuthenticationKey createAuthenticationKey(@NotNull Long userId, @NotNull String key, @NotNull LocalDateTime now) {
        return new AuthenticationKey(
                null,
                userId,
                key,
                null,
                now,
                now,
                null
        );
    }

    public boolean isExpired(LocalDateTime now) {
        return getCreatedAt().isBefore(now.minusMinutes(EXPIRED_MINUTES));
    }

    public boolean matchKey(String key, LocalDateTime now) {
        if (this.key.equals(key)) {
            this.verifiedAt = now;
            return true;
        }

        return false;
    }
}
