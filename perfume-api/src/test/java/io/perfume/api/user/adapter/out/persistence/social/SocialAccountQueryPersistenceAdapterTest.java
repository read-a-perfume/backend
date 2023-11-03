package io.perfume.api.user.adapter.out.persistence.social;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({TestQueryDSLConfiguration.class, SocialAccountQueryPersistenceAdapter.class,
    SocialAccountMapper.class,
    UserMapper.class})
@DataJpaTest
@EnableJpaAuditing
class SocialAccountQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private SocialAccountQueryPersistenceAdapter oauthQueryPersistenceAdapter;

  @Autowired
  private SocialAccountMapper oauthMapper;

  @DisplayName("identifier로 SocialAccount 조회")
  @Test
  void testFindByIdentifier() {
    // given
    String identifier = "testsocialidentifier";
    LocalDateTime now = LocalDateTime.now();
    SocialAccount socialAccount =
        SocialAccount.createGoogleSocialAccount(identifier, now);
    User user = User.generalUserJoin(
        "test", "test@mail.com", "test", false, false);
    socialAccount.connect(user);
    SocialAccountEntity entity = oauthMapper.toEntity(socialAccount);
    entityManager.persist(entity.user);
    entityManager.persist(entity);

    // when
    SocialAccount result =
        oauthQueryPersistenceAdapter.findOneBySocialId(identifier).orElseThrow();

    // then
    assertThat(result.getIdentifier()).isEqualTo(identifier);
    assertThat(result.getUser().getId()).isNotNegative();
  }

  @DisplayName("SocialAccount가 존재하지 않을 때 Optional empty 반환")
  @Test
  void testFindByIdentifierWhenNotExists() {
    // given
    String identifier = "testsocialidentifier";

    // when
    Optional<SocialAccount> result =
        oauthQueryPersistenceAdapter.findOneBySocialId(identifier);

    // then
    assertThat(result).isEmpty();
  }
}
