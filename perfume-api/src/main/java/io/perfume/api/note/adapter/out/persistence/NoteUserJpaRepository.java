package io.perfume.api.note.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

public interface NoteUserJpaRepository extends CrudRepository<NoteUserJpaEntity, Long> {
}
