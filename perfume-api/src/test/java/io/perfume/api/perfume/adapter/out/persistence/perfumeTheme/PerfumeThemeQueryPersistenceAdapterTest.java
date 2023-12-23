package io.perfume.api.perfume.adapter.out.persistence.perfumeTheme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.perfume.application.port.out.PerfumeThemeQueryRepository;
import io.perfume.api.perfume.domain.PerfumeTheme;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({PerfumeThemeQueryPersistenceAdapter.class, TestQueryDSLConfiguration.class})
@DataJpaTest
@EnableJpaAuditing
class PerfumeThemeQueryPersistenceAdapterTest {

  @Autowired private EntityManager em;

  @Autowired private PerfumeThemeQueryRepository perfumeThemeQueryRepository;

  @Test
  void getRecentTheme() {
    // given
    PerfumeThemeEntity perfumeThemeEntity =
        new PerfumeThemeEntity(1L, "title", "content", 1L, "1,3,5");
    em.persist(perfumeThemeEntity);
    em.flush();
    em.clear();

    // when
    Optional<PerfumeTheme> optionalPerfumeTheme = perfumeThemeQueryRepository.getRecentTheme();

    // then
    assertTrue(optionalPerfumeTheme.isPresent());
    PerfumeTheme perfumeTheme = optionalPerfumeTheme.get();
    assertEquals(perfumeTheme.getTitle(), perfumeThemeEntity.getTitle());
    assertEquals(perfumeTheme.getContent(), perfumeThemeEntity.getContent());
    assertThat(perfumeTheme.getPerfumeIds()).containsExactly(1L, 3L, 5L);
    assertThat(perfumeTheme.getCreatedAt()).isNotNull();
    assertThat(perfumeTheme.getUpdatedAt()).isNotNull();
    assertNull(perfumeTheme.getDeletedAt());
  }
}
