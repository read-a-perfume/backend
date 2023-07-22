package io.perfume.api.note.application.port.in;

import io.perfume.api.note.application.port.in.dto.NoteResult;
import java.util.List;

public interface FindNoteUseCase {

  List<NoteResult> findNotes();

  NoteResult findNoteById(Long id);
}
