package io.perfume.api.user.application.service;

import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.user.application.port.in.CreateUserTasteUseCase;
import io.perfume.api.user.application.port.in.FindUserTasteUseCase;
import io.perfume.api.user.application.port.in.dto.UserTasteResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserTasteService implements CreateUserTasteUseCase, FindUserTasteUseCase {

  private final FindNoteUseCase findNoteUseCase;

  public UserTasteService(FindNoteUseCase findNoteUseCase) {
    this.findNoteUseCase = findNoteUseCase;
  }

  @Override
  public UserTasteResult createUserTaste(Long userId, Long noteId) {
    return null;
  }

  @Override
  public List<UserTasteResult> getUserTastes(Long userId) {
    return null;
  }
}
