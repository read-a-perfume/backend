package io.perfume.api.note.application.service;

import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteUser;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CreateNoteService implements CreateNoteUseCase {

  private final NoteRepository noteRepository;

  private final FindNoteUseCase findNoteUseCase;

  public CreateNoteService(NoteRepository noteRepository, FindNoteUseCase findNoteUseCase) {
    this.noteRepository = noteRepository;
    this.findNoteUseCase = findNoteUseCase;
  }

  @Override
  public NoteResult createNote(CreateNoteCommand command) {
    Note savedNote =
        noteRepository.save(Note.create(command.name(), command.category(), command.thumbnailId()));

    return NoteResult.from(savedNote);
  }

  @Override
  public NoteResult createUserTaste(Long userId, Long noteId, LocalDateTime now) {
    NoteResult note = findNoteUseCase.findNoteById(noteId);
    noteRepository.save(NoteUser.create(userId, note.id(), now));

    return note;
  }
}
