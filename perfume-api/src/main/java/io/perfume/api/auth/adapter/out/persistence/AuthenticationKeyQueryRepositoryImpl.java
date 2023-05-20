package io.perfume.api.auth.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.auth.application.port.out.AuthenticationKeyQueryRepository;
import io.perfume.api.auth.domain.AuthenticationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthenticationKeyQueryRepositoryImpl implements AuthenticationKeyQueryRepository {

    private final AuthenticationKeyMapper authenticationKeyMapper;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<AuthenticationKey> findByKey(String key) {
        AuthenticationKeyJpaEntity authenticationKeyJpaEntity =
                jpaQueryFactory
                        .selectFrom(QAuthenticationKeyJpaEntity.authenticationKeyJpaEntity)
                        .where(
                                QAuthenticationKeyJpaEntity.authenticationKeyJpaEntity.signKey.eq(key),
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
