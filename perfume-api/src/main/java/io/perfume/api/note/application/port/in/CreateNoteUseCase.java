package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import java.time.LocalDateTime;

public interface CreateNoteUseCase {

    NoteResult createNote(CreateNoteCommand command);
}
