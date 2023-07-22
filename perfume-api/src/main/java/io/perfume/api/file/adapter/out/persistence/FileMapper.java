package io.perfume.api.file.adapter.out.persistence;

import io.perfume.api.file.domain.File;

public class FileMapper {
    public File toDomain(FileJpaEntity entity) {
        if(entity == null) {
            return null;
        }
        return File.withId(
                entity.getId(),
                entity.getUrl(),
                entity.getCreatedAt(),
                entity.getDeletedAt(),
                entity.getUpdatedAt()
        );
    }

}
