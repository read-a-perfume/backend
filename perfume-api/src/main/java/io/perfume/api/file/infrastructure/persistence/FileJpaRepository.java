package io.perfume.api.file.infrastructure.persistence;

import io.perfume.api.file.application.port.FileRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long>, FileRepository {
}
