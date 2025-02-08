package com.weatherservice.application.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

/**
 * <p>Dto создания истории погоды.</p>
 */
@Getter
@Builder
public class WeatherHistoryDto {

    /**
     * <p>Широта.</p>
     */
    private final Double latitude;

    /**
     * <p>Долгота.</p>
     */
    private final Double longitude;

    /**
     * <p>Погода.</p>
     */
    private final String weather;

    /**
     * <p>Дата получения погоды.</p>
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime queryDate;
}
