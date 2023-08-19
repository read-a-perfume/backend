package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.file.domain.File;
import jakarta.persistence.EntityManager;
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
        String url = "testUrl.com";
        LocalDateTime now = LocalDateTime.now();
        File file = File.createFile(url, 1L, now);
        FileJpaEntity fileJpaEntity = fileMapper.toEntity(file);
        entityManager.persist(fileJpaEntity);
        entityManager.clear();

        // when
        Optional<File> findFile = fileQueryRepository.findOneByFileId(fileJpaEntity.getId());

        // then
        assertThat(findFile).isPresent();
        assertEquals(url, findFile.get().getUrl());
    }

    @Test
    @DisplayName("id에 해당하는 File이 없을 때 에러를 반환한다.")
    void findFileById_not_found() {
        // given

        // when
        Optional<File> optionalFile = fileQueryRepository.findOneByFileId(1L);

        // then
        assertFalse(optionalFile.isPresent());
    }
}
