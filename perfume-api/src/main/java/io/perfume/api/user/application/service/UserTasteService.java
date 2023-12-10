package io.perfume.api.user.application.service;

import io.perfume.api.note.application.port.in.CreateCategoryUseCase;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.AddUserTasteCommand;
import io.perfume.api.user.application.port.in.CreateUserTasteUseCase;
import io.perfume.api.user.application.port.in.FindUserTasteUseCase;
import io.perfume.api.user.application.port.in.dto.UserTasteResult;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserTasteService implements CreateUserTasteUseCase, FindUserTasteUseCase {

  private final FindCategoryUseCase findCategoryUseCase;

  private final CreateCategoryUseCase createCategoryUseCase;

  public UserTasteService(
      FindCategoryUseCase findCategoryUseCase, CreateCategoryUseCase createCategoryUseCase) {
    this.findCategoryUseCase = findCategoryUseCase;
    this.createCategoryUseCase = createCategoryUseCase;
  }

  @Override
  public UserTasteResult createUserTaste(Long userId, Long categoryId) {
    LocalDateTime now = LocalDateTime.now();
    AddUserTasteCommand command = new AddUserTasteCommand(userId, categoryId);
    return UserTasteResult.from(createCategoryUseCase.create(command, now));
  }

  @Override
  public List<UserTasteResult> getUserTastes(Long userId) {
    return findCategoryUseCase.findTasteByUserId(userId).stream()
        .map(UserTasteResult::from)
        .toList();
  }
}
