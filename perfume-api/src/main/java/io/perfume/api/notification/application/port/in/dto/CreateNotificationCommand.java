package io.perfume.api.notification.application.port.in.dto;

import io.perfume.api.notification.domain.NotificationType;
import java.time.LocalDateTime;

public record CreateNotificationCommand(
    String content,
    String redirectUrl,
    Long receiveUserId,
    NotificationType notificationType,
    LocalDateTime now) {}
