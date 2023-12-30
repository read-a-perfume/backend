package io.perfume.api.notification.adapter.port.in.dto;

import io.perfume.api.notification.application.port.in.dto.GetNotificationCommand;

public record GetNotificationRequestDto(Integer pageSize, String before, String after) {
  public GetNotificationCommand toCommand() {
    return new GetNotificationCommand(getSizeOrDefault(), before, after);
  }

  private int getSizeOrDefault() {
    return pageSize == null ? 10 : pageSize;
  }
}
