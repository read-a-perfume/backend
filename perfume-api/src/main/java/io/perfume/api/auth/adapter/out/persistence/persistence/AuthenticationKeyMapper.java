package io.perfume.api.auth.adapter.out.persistence.persistence;

import io.perfume.api.auth.domain.AuthenticationKey;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationKeyMapper {

    AuthenticationKey toDomain(AuthenticationKeyJpaEntity authenticationKeyJpaEntity) {
        if (authenticationKeyJpaEntity == null) {
            return null;
        }

        return new AuthenticationKey(
                authenticationKeyJpaEntity.getId(),
                authenticationKeyJpaEntity.getUserId(),
                authenticationKeyJpaEntity.getKey(),
                authenticationKeyJpaEntity.getVerifiedAt(),
                authenticationKeyJpaEntity.getCreatedAt(),
                authenticationKeyJpaEntity.getUpdatedAt(),
                authenticationKeyJpaEntity.getDeletedAt()
        );
    }

    AuthenticationKeyJpaEntity toEntity(AuthenticationKey authenticationKey) {
        if (authenticationKey == null) {
            return null;
        }

        return new AuthenticationKeyJpaEntity(
                authenticationKey.getId(),
                authenticationKey.getUserId(),
                authenticationKey.getKey(),
                authenticationKey.getVerifiedAt(),
                authenticationKey.getCreatedAt(),
                authenticationKey.getUpdatedAt(),
                authenticationKey.getDeletedAt()
        );
    }
}
