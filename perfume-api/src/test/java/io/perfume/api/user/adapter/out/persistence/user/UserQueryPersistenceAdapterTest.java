package io.perfume.api.user.adapter.out.persistence.user;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
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

  @Autowired private EntityManager entityManager;

  @Autowired private UserQueryPersistenceAdapter userQueryPersistenceAdapter;

  @Test
  void findOneByEmail() {
    // given
    String email = "test@mail.com";
    LocalDateTime now = LocalDateTime.now();
    UserEntity userEntity =
        UserEntity.builder()
            .username("admin")
            .email(email)
            .password("admin")
            .sex(Sex.MALE)
            .role(Role.USER)
            .marketingConsent(false)
            .promotionConsent(false)
            .build();
    entityManager.persist(userEntity);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.findOneByEmail(email);

    // then
    assertThat(actual).isPresent();
    assertThat(actual.get().getEmail()).isEqualTo(email);
  }

  @Test
  void loadUser() {
    // given
    String username = "username";
    LocalDateTime now = LocalDateTime.now();
    UserEntity userEntity =
        UserEntity.builder()
            .username("admin")
            .email("admin@admin.com")
            .password("admin")
            .sex(Sex.MALE)
            .role(Role.USER)
            .marketingConsent(false)
            .promotionConsent(false)
            .build();
    entityManager.persist(userEntity);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.loadUser(userEntity.getId());

    // then
    assertThat(actual).isPresent();
    assertThat(actual.get().getId()).isEqualTo(userEntity.getId());
  }

  @Test
  void findByUsername() {
    // given
    String username = "username";
    LocalDateTime now = LocalDateTime.now();

    UserEntity userEntity =
        UserEntity.builder()
            .username(username)
            .email("admin@admin.com")
            .password("admin")
            .role(Role.USER)
            .sex(Sex.MALE)
            .role(Role.USER)
            .marketingConsent(false)
            .promotionConsent(false)
            .build();
    entityManager.persist(userEntity);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.findByUsername(username);

    // then
    assertThat(actual).isPresent();
    assertThat(actual.get().getUsername()).isEqualTo(username);
  }

  @Test
  void testFindOneByEmailOrUsername() {
    // given
    String username = "username";
    LocalDateTime now = LocalDateTime.now();
    UserEntity userEntity =
        UserEntity.builder()
            .username("admin")
            .email("admin@admin.com")
            .password("admin")
            .sex(Sex.MALE)
            .role(Role.USER)
            .marketingConsent(false)
            .promotionConsent(false)
            .build();
    entityManager.persist(userEntity);
    entityManager.flush();
    entityManager.clear();

    // when
    Optional<User> actual = userQueryPersistenceAdapter.findOneByEmailOrUsername(username);

    // then
    assertThat(actual).isNotPresent();
  }
}
