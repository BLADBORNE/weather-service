package com.weatherservice.application.backend.repository;

import com.weatherservice.application.backend.model.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * <p>Репозиторий модели {@link WeatherHistory}</p>
 */
public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, UUID> {
}
