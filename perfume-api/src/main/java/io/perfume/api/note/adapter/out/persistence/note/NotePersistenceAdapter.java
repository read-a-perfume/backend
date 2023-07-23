package io.perfume.api.note.adapter.out.persistence.note;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.note.adapter.out.persistence.noteUser.NoteUserJpaEntity;
import io.perfume.api.note.adapter.out.persistence.noteUser.NoteUserJpaRepository;
import io.perfume.api.note.adapter.out.persistence.noteUser.NoteUserMapper;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteUser;

@PersistenceAdapter
public class NotePersistenceAdapter implements NoteRepository {

  private final NoteMapper noteMapper;

  private final NoteUserMapper noteUserMapper;

  private final NoteJpaRepository noteJpaRepository;

  private final NoteUserJpaRepository noteUserJpaRepository;

  public NotePersistenceAdapter(NoteMapper noteMapper, NoteUserMapper noteUserMapper, NoteJpaRepository noteJpaRepository, NoteUserJpaRepository noteUserJpaRepository) {
    this.noteMapper = noteMapper;
    this.noteUserMapper = noteUserMapper;
    this.noteJpaRepository = noteJpaRepository;
    this.noteUserJpaRepository = noteUserJpaRepository;
  }

  @Override
  public Note save(Note note) {
    NoteJpaEntity noteEntity = noteMapper.toEntity(note);
    NoteJpaEntity savedNoteEntity = noteJpaRepository.save(noteEntity);

    return noteMapper.toDomain(savedNoteEntity);
  }

  @Override
  public NoteUser save(NoteUser noteUser) {
    NoteUserJpaEntity noteUserEntity = noteUserMapper.toEntity(noteUser);
    NoteUserJpaEntity savedNoteUserEntity = noteUserJpaRepository.save(noteUserEntity);

    return noteUserMapper.toDomain(savedNoteUserEntity);
  }
}
