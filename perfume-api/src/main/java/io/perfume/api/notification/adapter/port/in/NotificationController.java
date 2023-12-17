package io.perfume.api.notification.adapter.port.in;

import io.perfume.api.notification.application.facade.NotificationFacadeService;
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

  public NotificationController(NotificationFacadeService notificationService) {
    this.notificationService = notificationService;
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
}
