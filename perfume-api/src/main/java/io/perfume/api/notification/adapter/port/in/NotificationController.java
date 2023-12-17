package io.perfume.api.notification.adapter.port.in;

import io.perfume.api.notification.application.facade.NotificationFacadeService;
import io.perfume.api.notification.application.port.in.DeleteNotificationUseCase;
import io.perfume.api.notification.application.port.in.ReadNotificationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/notification")
public class NotificationController {

  private final NotificationFacadeService notificationService;

  private final ReadNotificationUseCase readNotificationUseCase;

  private final DeleteNotificationUseCase deleteNotificationUseCase;

  public NotificationController(NotificationFacadeService notificationService, ReadNotificationUseCase readNotificationUseCase, DeleteNotificationUseCase deleteNotificationUseCase) {
    this.notificationService = notificationService;
    this.readNotificationUseCase = readNotificationUseCase;
    this.deleteNotificationUseCase = deleteNotificationUseCase;
  }

  // TODO : 알림 조회 pagination

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
  @PatchMapping("/{notificationId}")
  public void readNotification(
          @AuthenticationPrincipal User user, @PathVariable Long notificationId) {
    var now = LocalDateTime.now();
    var userId = Long.parseLong(user.getUsername());
    readNotificationUseCase.read(userId, notificationId, now);
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{notificationId}")
  public void deleteNotification(
          @AuthenticationPrincipal User user, @PathVariable Long notificationId) {
    var now = LocalDateTime.now();
    var userId = Long.parseLong(user.getUsername());
    deleteNotificationUseCase.delete(userId, notificationId, now);
  }
}
