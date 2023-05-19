package io.perfume.api.auth.adapter.out.persistence.persistence;

import io.perfume.api.auth.domain.AuthenticationKey;
import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.sample.domain.Sample;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(TestQueryDSLConfiguration.class)
@DataJpaTest
@EnableJpaAuditing
class AuthenticationKeyQueryRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuthenticationKeyQueryRepositoryImpl authenticationKeyQueryRepository;

    @Test
    void findByKey() {
        // given
        LocalDateTime now = LocalDateTime.now();
        String code = "sample code";
        String key = "sample key";
        entityManager.persist(new AuthenticationKeyJpaEntity(null, code, key, null, now, now, null));
        entityManager.flush();
        entityManager.clear();

        // when
        Optional<AuthenticationKey> actual = authenticationKeyQueryRepository.findByKey(key);

        // then
        assertThat(actual.isPresent()).isTrue();
    }
}
