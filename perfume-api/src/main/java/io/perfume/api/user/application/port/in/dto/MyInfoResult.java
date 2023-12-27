package io.perfume.api.user.application.port.in.dto;

import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.domain.User;
import java.time.LocalDate;

public record MyInfoResult(
    Long userId,
    String username,
    String email,
    String bio,
    LocalDate birthday,
    Sex sex,
    String thumbnail) {
  public static MyInfoResult from(User user, String thumbnail) {
    return new MyInfoResult(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getBio(),
        user.getBirthday(),
        user.getSex(),
        thumbnail);
  }
}
