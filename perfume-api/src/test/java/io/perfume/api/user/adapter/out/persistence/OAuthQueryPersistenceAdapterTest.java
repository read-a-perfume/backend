package io.perfume.api.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.user.adapter.out.persistence.oauth.OAuthJpaEntity;
import io.perfume.api.user.adapter.out.persistence.oauth.OAuthMapper;
import io.perfume.api.user.adapter.out.persistence.oauth.OAuthQueryPersistenceAdapter;
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
@Import({TestQueryDSLConfiguration.class, OAuthQueryPersistenceAdapter.class, OAuthMapper.class,
    UserMapper.class})
@DataJpaTest
@EnableJpaAuditing
public class OAuthQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private OAuthQueryPersistenceAdapter oauthQueryPersistenceAdapter;

  @Autowired
  private OAuthMapper oauthMapper;

  @DisplayName("identifier로 SocialAccount 조회")
  @Test
  void testFindByIdentifier() {
    // given
    String identifier = "testsocialidentifier";
    LocalDateTime now = LocalDateTime.now();
    SocialAccount socialAccount =
        SocialAccount.createGoogleSocialAccount(identifier, "test@mail.com", now);
    User user = User.generalUserJoin(
        "test", "test@mail.com", "test", "test", false, false);
    socialAccount.link(user);
    OAuthJpaEntity entity = oauthMapper.toEntity(socialAccount);
    entityManager.persist(entity);

    // when
    SocialAccount result =
        oauthQueryPersistenceAdapter.findByIdentifier(identifier).orElseThrow();

    // then
    assertThat(result.getIdentifier()).isEqualTo(identifier);
    assertThat(result.getUser().getId()).isGreaterThanOrEqualTo(0L);
  }

  @DisplayName("SocialAccount가 존재하지 않을 때 Optional empty 반환")
  @Test
  void testFindByIdentifierWhenNotExists() {
    // given
    String identifier = "testsocialidentifier";

    // when
    Optional<SocialAccount> result =
        oauthQueryPersistenceAdapter.findByIdentifier(identifier);

    // then
    assertThat(result.isEmpty()).isTrue();
  }
}
