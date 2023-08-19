package io.perfume.api.file.adapter.out.persistence.file;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import io.perfume.api.file.domain.File;

import java.util.Optional;

@PersistenceAdapter
public class FileQueryPersistenceAdapter implements FileQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final FileMapper fileMapper;

    public FileQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory, FileMapper fileMapper) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.fileMapper = fileMapper;
    }

    @Override
    public Optional<File> findOneByFileId(Long fileId) {
        QFileJpaEntity file = QFileJpaEntity.fileJpaEntity;
        FileJpaEntity fileJpaEntity =
                jpaQueryFactory.selectFrom(file)
                .where(
                        file.id.eq(fileId)
                                .and(file.deletedAt.isNull()))
                .fetchOne();
        return Optional.ofNullable(fileMapper.toDomain(fileJpaEntity));
    }

    @Override
    public Optional<File> findOneByFileURL(String fileURL) {
        QFileJpaEntity file = QFileJpaEntity.fileJpaEntity;
        FileJpaEntity fileJpaEntity =
                jpaQueryFactory.selectFrom(file)
                        .where(
                                file.url.eq(fileURL)
                                        .and(file.deletedAt.isNull()))
                        .fetchOne();
        return Optional.ofNullable(fileMapper.toDomain(fileJpaEntity));
    }
}
