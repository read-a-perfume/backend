package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.application.port.in.dto.MyInfoResult;
import java.time.LocalDate;

public record MyInfoDto(
    Long userId,
    String username,
    String email,
    String bio,
    LocalDate birthday,
    Sex sex,
    String thumbnail) {
  public static MyInfoDto of(MyInfoResult myInfoResult) {
    return new MyInfoDto(
        myInfoResult.userId(),
        myInfoResult.username(),
        myInfoResult.email(),
        myInfoResult.bio(),
        myInfoResult.birthday(),
        myInfoResult.sex(),
        myInfoResult.thumbnail());
  }
}
