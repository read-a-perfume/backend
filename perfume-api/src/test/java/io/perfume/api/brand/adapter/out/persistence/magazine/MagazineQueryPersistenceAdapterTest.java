package io.perfume.api.brand.adapter.out.persistence.magazine;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.application.port.out.MagazineRepository;
import io.perfume.api.brand.domain.Magazine;
import io.perfume.api.configuration.TestQueryDSLConfiguration;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({
  MagazineQueryPersistenceAdapter.class,
  MagazinePersistenceAdapter.class,
  MagazineMapper.class,
  TestQueryDSLConfiguration.class
})
@DataJpaTest
class MagazineQueryPersistenceAdapterTest {

  @Autowired private EntityManager entityManager;
  @Autowired private MagazineQueryRepository magazineQueryRepository;
  @Autowired private MagazineRepository magazineRepository;
  @Autowired private MagazineMapper magazineMapper;

  @Test
  void testFindByMagazines() {
    var now = LocalDateTime.now();
    var magazine = new Magazine(1L, "title", "sub", "content", 1L, 1L, 1L, 1L, now, now, null);

    Magazine savedMagazine = magazineRepository.save(magazine);
    entityManager.flush();
    entityManager.clear();

    var results = magazineQueryRepository.findByMagazines(1L);
    assertThat(results.size()).isEqualTo(1);
  }
}
