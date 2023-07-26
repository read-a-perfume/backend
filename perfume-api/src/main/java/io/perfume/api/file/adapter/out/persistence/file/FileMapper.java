package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.file.domain.File;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public File toDomain(FileJpaEntity fileJpaEntity) {
        if (fileJpaEntity == null) return null;
        return File.builder()
                .id(fileJpaEntity.getId())
                .url(fileJpaEntity.getUrl())
                .createdAt(fileJpaEntity.getCreatedAt())
                .updatedAt(fileJpaEntity.getUpdatedAt())
                .deletedAt(fileJpaEntity.getDeletedAt())
                .build();
    }

    public FileJpaEntity toEntity(File file) {
        if (file == null) return null;
        return new FileJpaEntity(
                file.getId(),
                file.getUrl(),
                file.getCreatedAt(),
                file.getUpdatedAt(),
                file.getDeletedAt());
    }
}
