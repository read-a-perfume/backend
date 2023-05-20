package io.perfume.api.auth.adapter.out.persistence;

import io.perfume.api.auth.application.port.out.AuthenticationKeyRepository;
import io.perfume.api.auth.domain.AuthenticationKey;
import io.perfume.api.base.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class AuthenticationKeyRepositoryImpl implements AuthenticationKeyRepository {

    private final AuthenticationKeyMapper authenticationKeyMapper;

    private final AuthenticationKeyJpaRepository authenticationKeyJpaRepository;

    @Override
    public AuthenticationKey save(AuthenticationKey authenticationKey) {
        return authenticationKeyMapper.toDomain(
                authenticationKeyJpaRepository.save(
                        authenticationKeyMapper.toEntity(authenticationKey)
                )
        );
    }
}
