package io.perfume.api.file.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {
}
