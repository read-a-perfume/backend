package io.perfume.api.note.application.service;

import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import org.springframework.stereotype.Service;

@Service
public class CreateNoteService implements CreateNoteUseCase {

  private final NoteRepository noteRepository;

  public CreateNoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  public NoteResult createNote(CreateNoteCommand command) {
    Note savedNote =
        noteRepository.save(Note.create(command.name(), command.category(), command.thumbnailId()));

    return NoteResult.from(savedNote);
  }
}
