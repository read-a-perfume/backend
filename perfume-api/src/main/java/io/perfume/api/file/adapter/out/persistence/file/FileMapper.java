package io.perfume.api.file.adapter.out.persistence.file;

import io.perfume.api.file.domain.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

  public File toDomain(FileJpaEntity fileJpaEntity) {
    if (fileJpaEntity == null) {
      return null;
    }
    return File.builder()
        .id(fileJpaEntity.getId())
        .url(fileJpaEntity.getUrl())
        .userId(fileJpaEntity.getUserId())
        .createdAt(fileJpaEntity.getCreatedAt())
        .updatedAt(fileJpaEntity.getUpdatedAt())
        .deletedAt(fileJpaEntity.getDeletedAt())
        .build();
  }

  public FileJpaEntity toEntity(File file) {
    if (file == null) {
      return null;
    }
    return new FileJpaEntity(
        file.getId(),
        file.getUrl(),
        file.getUserId(),
        file.getCreatedAt(),
        file.getUpdatedAt(),
        file.getDeletedAt());
  }

  public List<File> toDomain(List<FileJpaEntity> fileJpaEntities) {
    if (fileJpaEntities == null) {
      return null;
    }
    List<File> files = new ArrayList<>();
    for (FileJpaEntity fileJpaEntity : fileJpaEntities) {
      files.add(
          File.builder()
              .id(fileJpaEntity.getId())
              .url(fileJpaEntity.getUrl())
              .userId(fileJpaEntity.getUserId())
              .createdAt(fileJpaEntity.getCreatedAt())
              .updatedAt(fileJpaEntity.getUpdatedAt())
              .deletedAt(fileJpaEntity.getDeletedAt())
              .build());
    }

    return files;
  }

  public List<FileJpaEntity> toEntity(List<File> files) {
    if (files == null) {
      return null;
    }
    List<FileJpaEntity> fileJpaEntities = new ArrayList<>();
    for (File file : files) {
      fileJpaEntities.add(
          new FileJpaEntity(
              file.getId(),
              file.getUrl(),
              file.getUserId(),
              file.getCreatedAt(),
              file.getUpdatedAt(),
              file.getDeletedAt()));
    }
    return fileJpaEntities;
  }
}
