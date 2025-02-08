package com.weatherservice.application.backend.service;

import com.weatherservice.application.backend.client.WeatherApiClient;
import com.weatherservice.application.backend.dto.WeatherDto;
import com.weatherservice.application.backend.mapper.WeatherMapper;
import com.weatherservice.application.backend.model.WeatherHistory;
import com.weatherservice.application.backend.repository.WeatherHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * <p>Сервис для работы с погодой.</p>
 */
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    /**
     * <p>Клиент для взаимодействия с API сервиса погоды.</p>
     *
     * <p>Клиент предоставляет метод для получения актуальной информации о погоде.</p>
     */
    private final WeatherApiClient client;

    /**
     * <p>Репозиторий истории погоды.</p>
     */
    private final WeatherHistoryRepository repository;

    @Override
    public WeatherDto getWeather(double latitude, double longitude) throws JSONException {

        LocalTime queryDate = LocalTime.now();
        WeatherDto weatherDto = WeatherMapper.toWeatherDto(client.getWeather(latitude, longitude).block());
        repository.save(WeatherMapper.toWeatherHistory(latitude, longitude, queryDate, weatherDto));

        return weatherDto;
    }

    @Override
    public List<WeatherHistory> getWeatherHistory() {

        return repository.findAll();
    }
}
