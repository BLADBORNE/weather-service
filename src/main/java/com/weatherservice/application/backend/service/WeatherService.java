package com.weatherservice.application.backend.service;

import com.weatherservice.application.backend.dto.WeatherDto;
import com.weatherservice.application.backend.model.WeatherHistory;
import org.json.JSONException;

import java.util.List;

/**
 * <p>Контракт для сервиса погоды</p>
 */
public interface WeatherService {

    /**
     * <p>Получение текущей погоды для заданных координат.</p>
     *
     * <p>Обращение к внешнему источнику данных для получения информации о погоде
     * на основе предоставленных широты и долготы. В случае успешного выполнения возвращает
     * объект {@link WeatherDto}, содержащий данные о текущих погодных условиях.</p>
     *
     * @param latitude  Широта.
     * @param longitude Долгота.
     *
     * @return Dto {@link WeatherDto}.
     *
     * @throws JSONException Если происходит ошибка при обработке данных о погоде.
     */
    WeatherDto getWeather(double latitude, double longitude) throws JSONException;

    /**
     * <p>Получение истории погоды.</p>
     *
     * @return Список моделей истории погоды List<{@link WeatherHistory}>.
     */
    List<WeatherHistory> getWeatherHistory();
}
