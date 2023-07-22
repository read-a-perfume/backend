package io.perfume.api.file.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import io.perfume.api.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@PersistenceAdapter
@RequiredArgsConstructor
public class FileQueryQueryPersistenceAdapter implements FileQueryRepository {

    // TODO QFileJpaEntity가 생성이 안되서 JpaRepository 임시 사용
    private final JPAQueryFactory jpaQueryFactory;
    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public Optional<File> loadFile(Long fileId) {
        FileJpaEntity fileJpaEntity = fileJpaRepository.findById(fileId).orElseGet(null);
        File nullableFile = fileMapper.toDomain(fileJpaEntity);
        return Optional.ofNullable(nullableFile);
    }
}
