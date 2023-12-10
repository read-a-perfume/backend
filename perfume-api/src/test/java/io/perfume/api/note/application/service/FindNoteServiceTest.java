package io.perfume.api.note.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import io.perfume.api.note.adapter.out.persistence.note.NoteQueryPersistenceAdapter;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.domain.Note;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindNoteServiceTest {

  @InjectMocks private FindNoteService findNoteService;
  @Mock private NoteQueryPersistenceAdapter noteQueryPersistenceAdapter;

  @Test
  void findNoteById() {
    // given
    Long noteId = 1L;
    LocalDateTime now = LocalDateTime.now();
    Note note = new Note(noteId, "white musk", "description", 1L, now, null, null);
    given(noteQueryPersistenceAdapter.findById(noteId)).willReturn(Optional.of(note));

    NoteResult noteResult = findNoteService.findNoteById(noteId);

    assertEquals(note.getId(), noteResult.id());
    assertEquals(note.getName(), noteResult.name());
    assertEquals(note.getDescription(), noteResult.description());
    assertEquals("", noteResult.thumbnail());
  }
}
