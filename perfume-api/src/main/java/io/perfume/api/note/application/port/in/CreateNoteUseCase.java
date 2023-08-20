package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.NoteResult;

public interface CreateNoteUseCase {

  NoteResult createNote(CreateNoteCommand command);
}
