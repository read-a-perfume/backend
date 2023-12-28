package io.perfume.api.notification.application.listener;

import io.perfume.api.notification.application.facade.NotificationFacadeService;
import io.perfume.api.review.application.facade.dto.ReviewCommentEvent;
import io.perfume.api.review.application.facade.dto.ReviewLikeEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificationListener {

  private final NotificationFacadeService notificationFacadeService;

  public NotificationListener(NotificationFacadeService notificationFacadeService) {
    this.notificationFacadeService = notificationFacadeService;
  }

  @TransactionalEventListener
  @Async
  public void reviewCommentNotificationHandler(ReviewCommentEvent event) {
    notificationFacadeService.reviewCommentNotifyOnEvent(event);
  }

  @TransactionalEventListener
  @Async
  public void reviewLikeNotificationHandler(ReviewLikeEvent event) {
    notificationFacadeService.reviewLikeNotifyOnEvent(event);
  }
}
