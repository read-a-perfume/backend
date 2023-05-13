package io.perfume.api.auth.domain;

import io.perfume.api.base.BaseTimeDomain;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthenticationKey extends BaseTimeDomain {

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
}
