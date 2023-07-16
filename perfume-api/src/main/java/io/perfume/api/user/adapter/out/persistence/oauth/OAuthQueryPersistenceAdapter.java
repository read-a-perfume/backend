package io.perfume.api.user.adapter.out.persistence.oauth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.OAuthQueryRepository;
import io.perfume.api.user.domain.SocialAccount;
import java.util.Objects;
import java.util.Optional;

@PersistenceAdapter
public class OAuthQueryPersistenceAdapter implements OAuthQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final OAuthMapper oauthMapper;

  public OAuthQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory, OAuthMapper oauthMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.oauthMapper = oauthMapper;
  }

  @Override
  public Optional<SocialAccount> findByIdentifier(String identifier) {
    OAuthJpaEntity entity = jpaQueryFactory.selectFrom(QOAuthJpaEntity.oAuthJpaEntity)
        .where(
            QOAuthJpaEntity.oAuthJpaEntity.identifier.eq(identifier)
                .and(QOAuthJpaEntity.oAuthJpaEntity.deletedAt.isNull()))
        .innerJoin(QOAuthJpaEntity.oAuthJpaEntity.user)
        .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(oauthMapper.toDomain(entity));
  }
}
