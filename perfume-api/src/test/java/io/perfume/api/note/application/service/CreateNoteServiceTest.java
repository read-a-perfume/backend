package io.perfume.api.note.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.application.port.in.dto.CreateNoteCommand;
import io.perfume.api.note.application.port.in.dto.NoteResult;
import io.perfume.api.note.stub.StubNoteRepository;
import org.junit.jupiter.api.Test;

class CreateNoteServiceTest {

  private final CreateNoteUseCase createNoteUseCase =
      new CreateNoteService(new StubNoteRepository());

  @Test
  void testCreateNote() {
    // given
    CreateNoteCommand command = new CreateNoteCommand("title", "", 1L);

    // when
    NoteResult result = createNoteUseCase.createNote(command);

    // then
    assertThat(result.name()).isEqualTo("title");
  }
}
