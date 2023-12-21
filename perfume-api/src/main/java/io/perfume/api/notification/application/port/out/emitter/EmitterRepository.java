package io.perfume.api.notification.application.port.out.emitter;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

  SseEmitter save(String emitterId, SseEmitter sseEmitter);

  void saveEventCache(String emitterId, Object event);

  Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

  Map<String, Object> findAllEventCacheStartWithByUserId(String userId);

  void deleteEmitterByEmitterId(String emitterId);

  void deleteAllEmitterStartWithId(String userId);

  void deleteCacheById(String id);

  void deleteAllEventCacheStartWithId(String userId);
}
