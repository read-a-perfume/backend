package io.perfume.api.notification.application.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SubscribeUseCase {

  SseEmitter subscribe(final String emitterId, final String lastEventId);
}
