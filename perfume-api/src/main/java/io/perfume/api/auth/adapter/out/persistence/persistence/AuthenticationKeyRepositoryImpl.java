package io.perfume.api.auth.adapter.out.persistence.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.auth.domain.AuthenticationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthenticationKeyRepositoryImpl implements AuthenticationKeyRepository, AuthenticationKeyQueryRepository {

    private final AuthenticationKeyJpaRepository authenticationKeyJpaRepository;

    private final AuthenticationKeyMapper authenticationKeyMapper;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AuthenticationKey save(AuthenticationKey authenticationKey) {
        return authenticationKeyMapper.toDomain(
                authenticationKeyJpaRepository.save(
                        authenticationKeyMapper.toEntity(authenticationKey)
                )
        );
    }

    @Override
    public Optional<AuthenticationKey> findByKey(String key) {
        AuthenticationKeyJpaEntity authenticationKeyJpaEntity =
                jpaQueryFactory
                        .selectFrom(QAuthenticationKeyJpaEntity.authenticationKeyJpaEntity)
                        .where(
                                QAuthenticationKeyJpaEntity.authenticationKeyJpaEntity.key.eq(key),
                                QAuthenticationKeyJpaEntity.authenticationKeyJpaEntity.deletedAt.isNull()
                        )
                        .fetchFirst();

        if (authenticationKeyJpaEntity == null) {
            return Optional.empty();
        }

        return Optional.of(
                authenticationKeyMapper.toDomain(authenticationKeyJpaEntity)
        );
    }
}
