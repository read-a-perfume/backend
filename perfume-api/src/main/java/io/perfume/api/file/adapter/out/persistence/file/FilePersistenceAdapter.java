package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.domain.File;

import java.util.Optional;

@PersistenceAdapter
public class FilePersistenceAdapter implements FileRepository {

    private final FileJpaRepository fileJpaRepository;

    private final FileMapper fileMapper;

    public FilePersistenceAdapter(FileJpaRepository fileJpaRepository, FileMapper fileMapper) {
        this.fileJpaRepository = fileJpaRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public Optional<File> save(File file) {
        return Optional.of(fileMapper.toDomain(
                fileJpaRepository.save(
                        fileMapper.toEntity(file)
                )
        ));
    }
}
