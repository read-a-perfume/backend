package io.perfume.api.notification.application.event;

import io.perfume.api.notification.application.facade.NotificationFacadeService;
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
  public <T> void notificationHandler(T item) {
    notificationFacadeService.notifyOnEvent(item);
  }
}
