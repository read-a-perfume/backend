package io.perfume.api.notification.application.port.in;

import io.perfume.api.notification.application.port.in.dto.NotificationResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SendNotificationUseCase {

  void send(NotificationResult notificationResult);

  void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter);

  void connectNotification(SseEmitter emitter, String emitterId, Long userId);
}
