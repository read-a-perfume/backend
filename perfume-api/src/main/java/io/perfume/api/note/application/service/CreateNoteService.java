package io.perfume.api.note.application.service;

import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.application.port.out.NoteQueryRepository;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.CategoryUser;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CreateNoteService implements CreateNoteUseCase {

  private final NoteRepository noteRepository;

  private final NoteQueryRepository noteQueryRepository;

  public CreateNoteService(NoteRepository noteRepository, NoteQueryRepository noteQueryRepository) {
    this.noteRepository = noteRepository;
    this.noteQueryRepository = noteQueryRepository;
  }

  @Override
  public NoteResult createNote(CreateNoteCommand command) {
    Note savedNote =
        noteRepository.save(Note.create(command.name(), command.category(), command.thumbnailId()));

    return NoteResult.from(savedNote);
  }

  @Override
  public NoteResult createUserTaste(Long userId, Long noteId, LocalDateTime now) {
    Note note = noteQueryRepository.findById(noteId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노트입니다."));
    noteRepository.save(CategoryUser.create(userId, note, now));

    return NoteResult.from(note);
  }
}
