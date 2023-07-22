package io.perfume.api.note.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.perfume.api.note.application.exception.NotFoundNoteException;
import io.perfume.api.note.stub.StubNoteQueryRepository;
import io.perfume.api.sample.application.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FindNoteServiceTest {

  private final StubNoteQueryRepository noteQueryRepository = new StubNoteQueryRepository();

  private final FindNoteService service = new FindNoteService(noteQueryRepository);

  @BeforeEach
  void beforeEach() {
    noteQueryRepository.clear();
  }

  @Test
  @DisplayName("존재하지 않는 노트를 조회하면 예외를 던진다.")
  void testFindNoteByIdWhenNoteExists() {
    // when & then
    assertThrows(NotFoundNoteException.class, () -> service.findNoteById(1L));
  }
}
