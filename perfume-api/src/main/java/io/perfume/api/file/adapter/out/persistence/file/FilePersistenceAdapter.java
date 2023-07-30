package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.file.application.port.out.FileRepository;
import io.perfume.api.file.domain.File;

import java.util.ArrayList;
import java.util.List;
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
    public File save(File file) {
        return fileMapper.toDomain(fileJpaRepository.save(fileMapper.toEntity(file)));
    }

    @Override
    public List<File> saveAll(List<File> files) {
        List<File> result = new ArrayList<>();
        for (File file : files) {
            result.add(file);
        }
        fileJpaRepository.saveAll(fileMapper.toEntity(result));
        return result;
    }
}
