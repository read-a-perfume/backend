package io.perfume.api.notification.application.service;

import io.perfume.api.notification.application.port.in.SubscribeUseCase;
import io.perfume.api.notification.application.port.out.emitter.EmitterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SubscribeService implements SubscribeUseCase {

  private final EmitterRepository emitterRepository;

  public SubscribeService(EmitterRepository emitterRepository) {
    this.emitterRepository = emitterRepository;
  }

  @Override
  public SseEmitter subscribe(final String emitterId, final String lastEventId) {
    final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;
    var emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
    emitter.onCompletion(() -> emitterRepository.deleteEmitterByEmitterId(emitterId));
    emitter.onTimeout(() -> emitterRepository.deleteEmitterByEmitterId(emitterId));
    emitter.onError((ec) -> emitterRepository.deleteEmitterByEmitterId(emitterId));
    return emitter;
  }
}
