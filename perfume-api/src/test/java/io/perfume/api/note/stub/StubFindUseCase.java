package io.perfume.api.note.stub;

import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import java.util.List;

public class StubFindUseCase implements FindNoteUseCase {

  @Override
  public List<NoteResult> findNotes() {
    return null;
  }

  @Override
  public NoteResult findNoteById(Long id) {
    return null;
  }

  @Override
  public NoteResult findNoteByUserId(Long id) {
    return null;
  }
}
