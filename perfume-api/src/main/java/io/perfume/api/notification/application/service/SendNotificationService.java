package io.perfume.api.notification.application.service;

import io.perfume.api.notification.application.port.in.SendNotificationUseCase;
import io.perfume.api.notification.application.port.in.dto.NotificationResult;
import io.perfume.api.notification.application.port.in.dto.SendNotificationResult;
import io.perfume.api.notification.application.port.out.emitter.EmitterRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SendNotificationService implements SendNotificationUseCase {

  private final EmitterRepository emitterRepository;

  public SendNotificationService(EmitterRepository emitterRepository) {
    this.emitterRepository = emitterRepository;
  }

  @Override
  public void send(NotificationResult notificationResult) {
    emitterRepository
        .findAllEmitterStartWithByUserId(String.valueOf(notificationResult.receiveUserId()))
        .forEach(
            (key, emitter) -> {
              emitterRepository.saveEventCache(key, notificationResult);
              sendNotification(emitter, key, SendNotificationResult.from(notificationResult));
            });
  }

  @Override
  public void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
    if (hasLostData(lastEventId)) {
      emitterRepository
          .findAllEventCacheStartWithByUserId(String.valueOf(userId))
          .entrySet()
          .stream()
          .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
          .forEach(entry -> sendNotification(emitter, entry.getKey(), entry.getValue()));
    }
  }

  @Override
  public void connectNotification(SseEmitter emitter, String emitterId, Long userId) {
    sendNotification(emitter, emitterId, "Connected to Server. [userId=" + userId + "]");
  }

  private void sendNotification(SseEmitter emitter, String emitterId, Object data) {
    try {
      emitter.send(SseEmitter.event().id(emitterId).data(data, MediaType.APPLICATION_JSON));
    } catch (Exception ex) {
      emitterRepository.deleteEmitterByEmitterId(emitterId);
      emitter.completeWithError(ex);
    }
  }

  private boolean hasLostData(String lastEventId) {
    return !lastEventId.isEmpty();
  }
}
