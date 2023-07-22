package io.perfume.api.note.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

public interface NoteJpaRepository extends CrudRepository<NoteJpaEntity, Long> {
}
