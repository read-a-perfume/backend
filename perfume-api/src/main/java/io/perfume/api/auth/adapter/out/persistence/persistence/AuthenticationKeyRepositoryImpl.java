package io.perfume.api.auth.adapter.out.persistence.persistence;

import io.perfume.api.auth.domain.AuthenticationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
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
