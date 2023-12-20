package io.perfume.api.notification.application.facade;

import io.perfume.api.notification.application.port.in.dto.CreateNotificationCommand;
import io.perfume.api.notification.application.service.CreateNotificationService;
import io.perfume.api.notification.application.service.SendNotificationService;
import io.perfume.api.notification.application.service.SubscribeService;
import io.perfume.api.notification.domain.NotificationType;
import io.perfume.api.review.application.facade.dto.ReviewCommentEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationFacadeService {

  private final CreateNotificationService createNotificationService;
  private final SubscribeService subScribeService;
  private final SendNotificationService sendNotificationService;

  public SseEmitter subscribe(long userId, String lastEventId) {
    String emitterId = createNotificationService.makeTimeIncludeId(userId);
    SseEmitter emitter = subScribeService.subscribe(emitterId, lastEventId);
    sendNotificationService.connectNotification(emitter, emitterId, userId);
    sendNotificationService.sendLostData(lastEventId, userId, emitterId, emitter);
    return emitter;
  }

  public void reviewCommentNotifyOnEvent(ReviewCommentEvent event) {
    var command = createReviewCommandNotification(event);
    var notificationResult = createNotificationService.create(command);
    sendNotificationService.send(notificationResult);
  }

  private CreateNotificationCommand createReviewCommandNotification(ReviewCommentEvent event) {
    var now = LocalDateTime.now();
    var content = event.reviewId() + "번 리뷰에 답글이 추가되었습니다.";
    var type = NotificationType.COMMENT;

    return CreateNotificationCommand.create(content, null, event.receiveUserId(), type, now);
  }
}
