package com.weatherservice.application.backend.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <p>Dto погоды по заданным координатам.</p>
 */
@Getter
@Builder
public final class WeatherDto {

    /**
     * <p>Погода.</p>
     */
    private final String weather;

    /**
     * <p>Температура.</p>
     */
    private final double temperature;

    /**
     * <p>Температура по ощущениям.</p>
     */
    private final double feelsLike;

    /**
     * <p>Путь к фотографии погоды </p>
     */
    private final String photoUrl;
}
