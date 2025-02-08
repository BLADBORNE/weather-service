package com.weatherservice.application.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

/**
 * <p>Модель история погоды.</p>
 */
@Getter
@Builder
@Entity
@Table(name = "weather_history")
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class WeatherHistory {

    /**
     * <p>Идентификатор.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    /**
     * <p>Широта.</p>
     */
    @Column(nullable = false)
    private final Double latitude;

    /**
     * <p>Долгота.</p>
     */
    @Column(nullable = false)
    private final Double longitude;

    /**
     * <p>Погода.</p>
     */
    @Column(nullable = false)
    private final String weather;

    /**
     * <p>Дата получения погоды.</p>
     */
    @Column(nullable = false, name = "query_date")
    private final LocalTime queryDate;
}
