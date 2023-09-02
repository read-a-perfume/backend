package io.perfume.api.user.adapter.out.persistence.social;

import static io.perfume.api.user.adapter.out.persistence.social.QSocialAccountEntity.socialAccountEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.SocialAccountQueryRepository;
import io.perfume.api.user.domain.SocialAccount;
import java.util.Objects;
import java.util.Optional;

@PersistenceAdapter
public class SocialAccountQueryPersistenceAdapter implements SocialAccountQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final SocialAccountMapper oauthMapper;

  public SocialAccountQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory,
                                              SocialAccountMapper oauthMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.oauthMapper = oauthMapper;
  }

  @Override
  public Optional<SocialAccount> findOneBySocialId(String socialId) {
    SocialAccountEntity entity =
        jpaQueryFactory.selectFrom(socialAccountEntity)
            .where(
                socialAccountEntity.identifier.eq(socialId)
                    .and(socialAccountEntity.deletedAt.isNull()))
            .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(oauthMapper.toDomain(entity));
  }
}
