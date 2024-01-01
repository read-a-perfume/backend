package io.perfume.api.user.application.service;

import io.perfume.api.note.application.port.in.CreateUserTypeUseCase;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.AddMyTypeCommand;
import io.perfume.api.user.application.port.in.UserTypeUseCase;
import io.perfume.api.user.application.port.in.dto.UserTypeResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTypeService implements UserTypeUseCase {

  private final FindCategoryUseCase findCategoryUseCase;
  private final CreateUserTypeUseCase createUserTypeUseCase;

  @Override
  public void createUserType(Long userId, Long categoryId) {
    LocalDateTime now = LocalDateTime.now();
    AddMyTypeCommand command = new AddMyTypeCommand(userId, categoryId);
    createUserTypeUseCase.create(command, now);
  }

  @Override
  public List<UserTypeResult> getUserTypes(Long userId) {
    return findCategoryUseCase.findTypeByUserId(userId).stream().map(UserTypeResult::from).toList();
  }
}
