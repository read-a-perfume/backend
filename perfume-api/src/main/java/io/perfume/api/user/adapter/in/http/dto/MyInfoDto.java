package io.perfume.api.user.adapter.in.http.dto;

import io.perfume.api.user.application.port.in.dto.MyInfoResult;

public record MyInfoDto(Long userId, String username, String thumbnail) {
  public static MyInfoDto of(MyInfoResult myInfoResult) {
    return new MyInfoDto(myInfoResult.userId(), myInfoResult.username(), myInfoResult.thumbnail());
  }
}
