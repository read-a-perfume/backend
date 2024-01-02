package io.perfume.api.user.application.service;

import io.perfume.api.user.application.exception.AddMyTypeException;
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
  public void addUserTypes(Long userId, List<Long> categoryIds) {
    LocalDateTime now = LocalDateTime.now();
    AddMyTypeCommand command = new AddMyTypeCommand(userId, categoryIds);

    if(command.categoryIds().size() > 3) {
      throw new AddMyTypeException("내 타입은 최대 3개까지만 지정 가능합니다.");
    }

    createUserTypeUseCase.create(command, now);
  }

  @Override
  public List<UserTypeResult> getUserTypes(Long userId) {
    return findCategoryUseCase.findTypeByUserId(userId).stream().map(UserTypeResult::from).toList();
  }
}
