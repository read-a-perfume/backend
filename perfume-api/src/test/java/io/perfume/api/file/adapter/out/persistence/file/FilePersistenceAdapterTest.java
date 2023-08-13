package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.domain.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import({FilePersistenceAdapter.class, FileMapper.class})
@DataJpaTest
@EnableJpaAuditing
class FilePersistenceAdapterTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    @DisplayName("파일을 저장한다.")
    void testSave() {
        // given
        LocalDateTime now = LocalDateTime.now();
        File file = File.createFile("test-url.com", null, now);

        // when
        File createFile = fileRepository.save(file);

        // then
        assertThat(createFile.getId()).isEqualTo(1L);
        assertThat(createFile.getUserId()).isEqualTo(null);
        assertThat(createFile.getUrl()).isEqualTo("test-url.com");
    }
}