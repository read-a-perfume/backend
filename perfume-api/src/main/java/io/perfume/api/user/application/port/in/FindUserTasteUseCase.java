package io.perfume.api.user.application.port.in;

import io.perfume.api.user.application.port.in.dto.UserTasteResult;
import java.util.List;

public interface FindUserTasteUseCase {

  List<UserTasteResult> getUserTastes(Long userId);
}
