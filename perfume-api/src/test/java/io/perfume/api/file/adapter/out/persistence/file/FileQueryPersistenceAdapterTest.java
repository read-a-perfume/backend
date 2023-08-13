package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.file.domain.File;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Import({FileQueryPersistenceAdapter.class, FileMapper.class, TestQueryDSLConfiguration.class})
@DataJpaTest
@EnableJpaAuditing
class FileQueryPersistenceAdapterTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FileQueryPersistenceAdapter fileQueryRepository;

    @Autowired
    private FileMapper fileMapper;

    @Test
    @DisplayName("특정 Id File 조회")
    void testFindById() {
        //given
        LocalDateTime now = LocalDateTime.now();
        File file = File.createFile("testUrl.com", 1L, now);
        FileJpaEntity fileJpaEntity = fileMapper.toEntity(file);
        entityManager.persist(fileJpaEntity);
        entityManager.clear();

        // when
        Optional<File> findFile = fileQueryRepository.findOneByFileId(1L);

        // then
        assertThat(findFile).isPresent();
        assertThat(findFile.get().getUrl()).isEqualTo("testUrl.com");
    }
}