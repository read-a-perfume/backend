package io.perfume.api.user.adapter.in.http.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.application.port.in.dto.UpdateProfileCommand;
import java.time.LocalDate;

public record UpdateProfileRequestDto(
    String bio, @JsonFormat(pattern = "yyyyMMdd") LocalDate birthday, String sex) {
  public UpdateProfileCommand toCommand(long userId) {
    return new UpdateProfileCommand(
        userId, bio, birthday, sex != null ? Sex.valueOf(sex.toUpperCase()) : null);
  }
}
