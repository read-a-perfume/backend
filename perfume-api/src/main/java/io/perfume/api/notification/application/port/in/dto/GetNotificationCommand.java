package io.perfume.api.notification.application.port.in.dto;

import dto.repository.CursorDirection;

public record GetNotificationCommand(int pageSize, String before, String after) {
  public String getCursor() {
    if (before != null) return before;
    return after;
  }

  public boolean hasNext() {
    return after != null;
  }

  public boolean hasPrevious() {
    return before != null;
  }

  public CursorDirection getDirection() {
    if (before != null) return CursorDirection.PREVIOUS;
    return CursorDirection.NEXT;
  }
}
