package io.perfume.api.file.adapter.out.persistence.file;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.file.application.port.out.FileQueryRepository;
import io.perfume.api.file.domain.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        jpaQueryFactory
            .selectFrom(file)
            .where(file.id.eq(fileId).and(file.deletedAt.isNull()))
            .fetchOne();
    return Optional.ofNullable(fileMapper.toDomain(fileJpaEntity));
  }

  @Override
  public Optional<File> findOneByFileURL(String fileURL) {
    QFileJpaEntity file = QFileJpaEntity.fileJpaEntity;
    FileJpaEntity fileJpaEntity =
        jpaQueryFactory
            .selectFrom(file)
            .where(file.url.eq(fileURL).and(file.deletedAt.isNull()))
            .fetchOne();
    return Optional.ofNullable(fileMapper.toDomain(fileJpaEntity));
  }

  @Override
  public List<File> findByIds(final List<Long> fileIds) {
    if (fileIds == null || fileIds.isEmpty()) {
      return Collections.emptyList();
    }

    QFileJpaEntity file = QFileJpaEntity.fileJpaEntity;
    List<FileJpaEntity> fileJpaEntities =
        jpaQueryFactory
            .selectFrom(file)
            .where(
                file.id
                    .in(fileIds.stream().filter(Objects::nonNull).toList())
                    .and(file.deletedAt.isNull()))
            .fetch();

    return fileJpaEntities.stream().map(fileMapper::toDomain).toList();
  }
}
