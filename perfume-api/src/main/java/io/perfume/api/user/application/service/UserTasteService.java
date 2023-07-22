package io.perfume.api.user.application.service;

import io.perfume.api.note.application.port.in.CreateNoteUseCase;
import io.perfume.api.note.application.port.in.FindNoteUseCase;
import io.perfume.api.user.application.port.in.CreateUserTasteUseCase;
import io.perfume.api.user.application.port.in.FindUserTasteUseCase;
import io.perfume.api.user.application.port.in.dto.UserTasteResult;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserTasteService implements CreateUserTasteUseCase, FindUserTasteUseCase {

  private final FindNoteUseCase findNoteUseCase;

  private final CreateNoteUseCase createNoteUseCase;

  public UserTasteService(FindNoteUseCase findNoteUseCase, CreateNoteUseCase createNoteUseCase) {
    this.findNoteUseCase = findNoteUseCase;
    this.createNoteUseCase = createNoteUseCase;
  }

  @Override
  public UserTasteResult createUserTaste(Long userId, Long noteId) {
    LocalDateTime now = LocalDateTime.now();
    return UserTasteResult.from(createNoteUseCase.createUserTaste(userId, noteId, now));
  }

  @Override
  public List<UserTasteResult> getUserTastes(Long userId) {
    return findNoteUseCase.findUserNotesByUserId(userId).stream().map(UserTasteResult::from).toList();
  }
}
