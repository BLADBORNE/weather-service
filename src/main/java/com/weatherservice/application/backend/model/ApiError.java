package com.weatherservice.application.backend.model;

import java.time.LocalDateTime;

/**
 * <p>Модель ошибка.</p>
 *
 * @param status Статус.
 * @param reason Причина.
 * @param message Сообщение.
 * @param timestamp Дата.
 */
public record ApiError(
        String status,
        String reason,
        String message,
        LocalDateTime timestamp
) {
}
