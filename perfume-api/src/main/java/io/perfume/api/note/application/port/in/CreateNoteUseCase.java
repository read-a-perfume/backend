package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.CreateNoteResult;

public interface CreateNoteUseCase {

    CreateNoteResult createNote(CreateNoteCommand command);
}
