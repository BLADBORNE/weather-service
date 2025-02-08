package com.weatherservice.application.backend.controller.error;

import com.weatherservice.application.backend.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * <p>Обработчик исключений сервиса прогноза погоды.</p>
 */
@RestControllerAdvice
@Slf4j
public class WeatherServiceErrorHandler {

    /**
     * <p>Обработчик INTERNAL_SERVER_ERROR.</p>
     *
     * @return модель исключения {@link ApiError}.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowableException(final Throwable e) {

        log.error("{}: {}", "Произошла непредвиденная ошибка.", e.getMessage());

        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Произошла непредвиденная ошибка.",
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
