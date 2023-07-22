package io.perfume.api.note.application.service;

import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.stub.StubNoteRepository;

class CreateNoteServiceTest {

  private final CreateNoteUseCase createNoteUseCase =
      new CreateNoteService(new StubNoteRepository());
}
