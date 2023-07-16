package io.perfume.api.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.user.adapter.out.persistence.user.UserJpaEntity;
import io.perfume.api.user.adapter.out.persistence.user.UserQueryPersistenceAdapter;
import io.perfume.api.user.domain.Role;
import io.perfume.api.user.domain.User;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(TestQueryDSLConfiguration.class)
@DataJpaTest
@EnableJpaAuditing
class UserQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private UserQueryPersistenceAdapter userQueryPersistenceAdapter;

  @Test
  void findOneByEmail() {
    // given
    String email = "test@mail.com";
    LocalDateTime now = LocalDateTime.now();
    entityManager.persist(
        new UserJpaEntity(
            null, "username", email, "abcd", "abcd", Role.USER, false, false, now, now, null
        )
    );
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.findOneByEmail(email);

    // then
    assertThat(actual.isPresent()).isTrue();
    assertThat(actual.get().getEmail()).isEqualTo(email);
  }

  @Test
  void loadUser() {
    // given
    String username = "username";
    LocalDateTime now = LocalDateTime.now();
    UserJpaEntity entity = new UserJpaEntity(
        null, username, "test@mail.com", "abcd", "abcd", Role.USER, false, false, now, now, null
    );
    entityManager.persist(entity);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.loadUser(entity.getId());

    // then
    assertThat(actual.isPresent()).isTrue();
    assertThat(actual.get().getId()).isEqualTo(entity.getId());
  }

  @Test
  void findByUsername() {
    // given
    String username = "username";
    LocalDateTime now = LocalDateTime.now();
    entityManager.persist(
        new UserJpaEntity(
            null, username, "test@mail.com", "abcd", "abcd", Role.USER, false, false, now, now, null
        ));
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.findByUsername(username);

    // then
    assertThat(actual.isPresent()).isTrue();
    assertThat(actual.get().getUsername()).isEqualTo(username);
  }
}
