package io.perfume.api.note.application.service;

import io.perfume.api.note.application.exception.NotFoundNoteException;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.application.port.out.NoteQueryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FindNoteService implements FindNoteUseCase {

  private final NoteQueryRepository noteQueryRepository;

  public FindNoteService(NoteQueryRepository noteQueryRepository) {
    this.noteQueryRepository = noteQueryRepository;
  }

  @Override
  public List<NoteResult> findNotes() {
    return noteQueryRepository.find().stream().map(NoteResult::from).toList();
  }

  @Override
  public NoteResult findNoteById(Long id) {
    return noteQueryRepository.findById(id).map(NoteResult::from)
        .orElseThrow(() -> new NotFoundNoteException(id));
  }

  @Override
  public List<NoteResult> findUserNotesByUserId(Long id) {
    return noteQueryRepository.findUserNotesByUserId(id).stream().map(NoteResult::from).toList();
  }
}
