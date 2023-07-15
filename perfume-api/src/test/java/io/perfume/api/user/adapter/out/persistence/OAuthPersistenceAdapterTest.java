package io.perfume.api.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.user.adapter.out.persistence.oauth.OAuthMapper;
import io.perfume.api.user.adapter.out.persistence.oauth.OAuthPersistenceAdapter;
import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.application.port.out.OAuthRepository;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
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
@Import({OAuthPersistenceAdapter.class, OAuthMapper.class, UserMapper.class})
@DataJpaTest
@EnableJpaAuditing
class OAuthPersistenceAdapterTest {

  @Autowired
  private OAuthPersistenceAdapter oauthRepository;

  @Test
  @DisplayName("소셜 계정 정보를 저장한다.")
  public void save() {
    // given
    LocalDateTime now = LocalDateTime.now();
    SocialAccount socialAccount = SocialAccount.createGoogleSocialAccount("test", "test@mail.com", now);
    User user = User.generalUserJoin(
        "test", "test@mail.com", "test", "test", false, false);
    socialAccount.link(user);

    // when
    SocialAccount createdSocialAccount = oauthRepository.save(socialAccount).orElseThrow();

    // then
    assertThat(createdSocialAccount.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdSocialAccount.getUserId()).isGreaterThanOrEqualTo(0L);
  }
}
