package io.perfume.api.file.adapter.out.persistence.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.perfume.api.file.domain.File;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({FilePersistenceAdapter.class, FileMapper.class})
@DataJpaTest
@EnableJpaAuditing
class FilePersistenceAdapterTest {

  @Autowired private FilePersistenceAdapter fileRepository;

  @Test
  @DisplayName("파일을 저장한다.")
  void testSave() {
    // given
    LocalDateTime now = LocalDateTime.now();
    File file = File.createFile("test-url.com", 1L, now);

    // when
    File createFile = fileRepository.save(file);

    // then
    assertThat(createFile.getId()).isEqualTo(1L);
    assertThat(createFile.getUserId()).isEqualTo(1L);
    assertThat(createFile.getUrl()).isEqualTo("test-url.com");
  }

  @Test
  @DisplayName("파일을 저장할 때 user 정보가 없으면 에러가 발생한다.")
  void testSave_Null() {
    // given
    LocalDateTime now = LocalDateTime.now();
    File file = File.createFile("test-url.com", null, null);

    // when
    // then
    assertThrows(ConstraintViolationException.class, () -> fileRepository.save(file));
  }
}
