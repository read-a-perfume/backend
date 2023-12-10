package io.perfume.api.user.adapter.out.persistence.social;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({SocialAccountPersistenceAdapter.class, SocialAccountMapper.class, UserMapper.class})
@DataJpaTest
@EnableJpaAuditing
class SocialAccountPersistenceAdapterTest {

  @Autowired private SocialAccountPersistenceAdapter oauthRepository;

  @Test
  @DisplayName("소셜 계정 정보를 저장한다.")
  void save() {
    // given
    LocalDateTime now = LocalDateTime.now();
    SocialAccount socialAccount = SocialAccount.createGoogleSocialAccount("test", now);
    User user = User.generalUserJoin("test", "test@mail.com", "test", false, false);
    socialAccount.connect(user);

    // when
    SocialAccount createdSocialAccount = oauthRepository.save(socialAccount).orElseThrow();

    // then
    assertThat(createdSocialAccount.getId()).isNotNegative();
  }
}
