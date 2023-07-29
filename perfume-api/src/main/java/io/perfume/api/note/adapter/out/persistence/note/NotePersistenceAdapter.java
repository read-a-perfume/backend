package io.perfume.api.note.adapter.out.persistence.note;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.note.adapter.out.persistence.noteUser.CategoryUserJpaEntity;
import io.perfume.api.note.adapter.out.persistence.noteUser.CategoryUserJpaRepository;
import io.perfume.api.note.adapter.out.persistence.noteUser.CategoryUserMapper;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.CategoryUser;

@PersistenceAdapter
public class NotePersistenceAdapter implements NoteRepository {

  private final NoteMapper noteMapper;

  private final CategoryUserMapper categoryUserMapper;

  private final NoteJpaRepository noteJpaRepository;

  private final CategoryUserJpaRepository categoryUserJpaRepository;

  public NotePersistenceAdapter(NoteMapper noteMapper, CategoryUserMapper categoryUserMapper, NoteJpaRepository noteJpaRepository, CategoryUserJpaRepository categoryUserJpaRepository) {
    this.noteMapper = noteMapper;
    this.categoryUserMapper = categoryUserMapper;
    this.noteJpaRepository = noteJpaRepository;
    this.categoryUserJpaRepository = categoryUserJpaRepository;
  }

  @Override
  public Note save(Note note) {
    NoteJpaEntity noteEntity = noteMapper.toEntity(note);
    NoteJpaEntity savedNoteEntity = noteJpaRepository.save(noteEntity);

    return noteMapper.toDomain(savedNoteEntity);
  }

  @Override
  public CategoryUser save(CategoryUser categoryUser) {
    CategoryUserJpaEntity noteUserEntity = categoryUserMapper.toEntity(categoryUser);
    CategoryUserJpaEntity savedNoteUserEntity = categoryUserJpaRepository.save(noteUserEntity);

    return categoryUserMapper.toDomain(savedNoteUserEntity);
  }
}
