package io.perfume.api.notification.application.facade;

import io.perfume.api.notification.application.exception.NotFoundNotificationTypeException;
import io.perfume.api.notification.application.port.in.dto.CreateNotificationCommand;
import io.perfume.api.notification.application.service.CreateNotificationService;
import io.perfume.api.notification.application.service.SendNotificationService;
import io.perfume.api.notification.application.service.SubscribeService;
import io.perfume.api.notification.domain.NotificationType;
import io.perfume.api.review.domain.ReviewComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationFacadeService {

  private final CreateNotificationService createNotificationService;
  private final SubscribeService subScribeService;
  private final SendNotificationService sendNotificationService;

  @Transactional
  public SseEmitter subscribe(long userId, String lastEventId) {
    String emitterId = createNotificationService.makeTimeIncludeId(userId);
    SseEmitter emitter = subScribeService.subscribe(emitterId, lastEventId);
    sendNotificationService.connectNotification(emitter, emitterId, userId);
    sendNotificationService.sendLostData(lastEventId, userId, emitterId, emitter);
    return emitter;
  }

  public <T> void notifyOnEvent(T item) {
    var command = create(item).orElseThrow(NotFoundNotificationTypeException::new);
    var notificationResult = createNotificationService.create(command);
    sendNotificationService.send(notificationResult);
  }

  private <T> Optional<CreateNotificationCommand> create(T item) {
    var now = LocalDateTime.now();
    if (item instanceof ReviewComment comment) {
      var content = comment.getId() + "번 리뷰의 답글이 추가되었습니다.";
      var type = NotificationType.COMMENT;

      return Optional.of(
          CreateNotificationCommand.create(content, null, comment.getUserId(), type, now));
    }
    return Optional.empty();
  }
}
