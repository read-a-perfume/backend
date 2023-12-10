package io.perfume.api.note.application.service;

import io.perfume.api.note.adapter.out.persistence.note.NoteQueryPersistenceAdapter;
import io.perfume.api.note.application.exception.NotFoundNoteException;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.domain.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindNoteService implements FindNoteUseCase {

  private final NoteQueryPersistenceAdapter noteQueryPersistenceAdapter;

  @Override
  public NoteResult findNoteById(Long noteId) {
    Note note =
        noteQueryPersistenceAdapter
            .findById(noteId)
            .orElseThrow(() -> new NotFoundNoteException(noteId));
    return NoteResult.from(note);
  }
}
