package io.perfume.api.note.adapter.out.persistence;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;

@PersistenceAdapter
public class NotePersistenceAdapter implements NoteRepository {

  private final NoteMapper noteMapper;

  private final NoteJpaRepository noteJpaRepository;

  public NotePersistenceAdapter(NoteMapper noteMapper, NoteJpaRepository noteJpaRepository) {
    this.noteMapper = noteMapper;
    this.noteJpaRepository = noteJpaRepository;
  }

  @Override
  public Note save(Note note) {
    NoteJpaEntity noteEntity = noteMapper.toEntity(note);
    NoteJpaEntity savedNoteEntity = noteJpaRepository.save(noteEntity);

    return noteMapper.toDomain(savedNoteEntity);
  }
}
