package io.perfume.api.brand.adapter.out.persistence.magazine;

import static org.assertj.core.api.Assertions.assertThat;

import dto.repository.CursorDirection;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.application.port.out.MagazineRepository;
import io.perfume.api.brand.domain.Magazine;
import io.perfume.api.configuration.TestQueryDSLConfiguration;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
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

//  @Test
//  @DisplayName("매거진 테스트 코드 조회")
//  void testMagazine() {
//    // given
//    final long branId = 1L;
//    final LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
//    final List<Magazine> magazines =
//        IntStream.range(0, 15)
//            .mapToObj(
//                (index) ->
//                    Magazine.create(
//                        "test", "test", "test", 1L, 1L, 1L, branId, now.plusSeconds(1000L * index)))
//            .map(magazineRepository::save)
//            .toList();
//    final String cursor =
//        Base64.encodeBase64String(magazines.get(14).getCreatedAt().toString().getBytes());
//    final CursorPageable pageable = new CursorPageable(2L, CursorDirection.NEXT, cursor);
//
//    // when
//    final CursorPagination<Magazine> result =
//        magazineQueryRepository.findByBrandId(pageable, branId);
//
//    // then
//    assertThat(result.getItems()).hasSize(2);
//    assertThat(result.getItems().get(0).getId()).isEqualTo(magazines.get(13).getId());
//    assertThat(result.getItems().get(1).getId()).isEqualTo(magazines.get(12).getId());
//    assertThat(result.hasNext()).isTrue();
//    assertThat(result.hasPrevious()).isTrue();
//  }
}
