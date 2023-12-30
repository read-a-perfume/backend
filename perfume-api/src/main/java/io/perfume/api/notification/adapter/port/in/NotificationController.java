package io.perfume.api.notification.adapter.port.in;

import dto.ui.CursorResponse;
import io.perfume.api.notification.adapter.port.in.dto.DeleteNotificationResponseDto;
import io.perfume.api.notification.adapter.port.in.dto.GetNotificationRequestDto;
import io.perfume.api.notification.adapter.port.in.dto.GetNotificationResponseDto;
import io.perfume.api.notification.application.facade.NotificationFacadeService;
import io.perfume.api.notification.application.port.in.DeleteNotificationUseCase;
import io.perfume.api.notification.application.port.in.GetNotificationUseCase;
import io.perfume.api.notification.application.port.in.ReadNotificationUseCase;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/v1/notification")
public class NotificationController {

  private final NotificationFacadeService notificationService;

  private final ReadNotificationUseCase readNotificationUseCase;

  private final DeleteNotificationUseCase deleteNotificationUseCase;

  private final GetNotificationUseCase getNotificationUseCase;

  public NotificationController(
      NotificationFacadeService notificationService,
      ReadNotificationUseCase readNotificationUseCase,
      DeleteNotificationUseCase deleteNotificationUseCase,
      GetNotificationUseCase getNotificationUseCase) {
    this.notificationService = notificationService;
    this.readNotificationUseCase = readNotificationUseCase;
    this.deleteNotificationUseCase = deleteNotificationUseCase;
    this.getNotificationUseCase = getNotificationUseCase;
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping()
  public ResponseEntity<CursorResponse<GetNotificationResponseDto>> getNotifications(
      @AuthenticationPrincipal User user, GetNotificationRequestDto request) {
    var userId = Long.parseLong(user.getUsername());
    var notifications = getNotificationUseCase.getNotifications(userId, request.toCommand());
    final var responseItems =
        notifications.getItems().stream().map(GetNotificationResponseDto::from).toList();
    return ResponseEntity.ok(
        CursorResponse.of(
            responseItems,
            notifications.hasNext(),
            notifications.hasPrevious(),
            notifications.getNextCursor(),
            notifications.getPreviousCursor()));
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> subscriber(
      @AuthenticationPrincipal User user,
      @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
          String lastEventId) {
    var userId = Long.parseLong(user.getUsername());
    var resEmitter = notificationService.subscribe(userId, lastEventId);
    return ResponseEntity.status(HttpStatus.OK).body(resEmitter);
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/{id}")
  public ResponseEntity<Void> readNotification(
      @AuthenticationPrincipal User user, @PathVariable("id") Long notificationId) {
    var now = LocalDateTime.now();
    var userId = Long.parseLong(user.getUsername());
    readNotificationUseCase.read(userId, notificationId, now);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteNotificationResponseDto> deleteNotification(
      @AuthenticationPrincipal User user, @PathVariable("id") Long notificationId) {
    var now = LocalDateTime.now();
    var userId = Long.parseLong(user.getUsername());
    deleteNotificationUseCase.delete(userId, notificationId, now);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DeleteNotificationResponseDto(notificationId));
  }
}
