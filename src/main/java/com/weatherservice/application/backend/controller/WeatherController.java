package com.weatherservice.application.backend.controller;

import com.weatherservice.application.backend.dto.WeatherDto;
import com.weatherservice.application.backend.dto.WeatherHistoryDto;
import com.weatherservice.application.backend.mapper.WeatherMapper;
import com.weatherservice.application.backend.model.WeatherHistory;
import com.weatherservice.application.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Контроллер для работы с погодой.</p>
 */
@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public final class WeatherController {

    /**
     * <p>Сервис погоды.</p>
     */
    private final WeatherService weatherService;

    /**
     * <p>Получение данных о погоде по заданным координатам.</p>
     *
     * @param latitude Широта.
     * @param longitude Долгота.
     *
     * @return Dto погоды {@link WeatherDto}.
     *
     * @throws JSONException Если возникает ошибка при обработке JSON-данных.
     */
    @GetMapping
    public WeatherDto getWeather(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) throws JSONException {

        log.info("Получен запрос на получение погоды по широте: {} и долготе: {}", latitude, longitude);

        WeatherDto weather = weatherService.getWeather(latitude, longitude);

        log.info("Успешно отправлены данные о погоде");

        return weather;
    }

    /**
     * <p>Получение истории просмотра погоды.</p>
     *
     * @return Список моделей истории погоды List<{@link WeatherHistory}>.
     */
    @GetMapping("/history")
    public List<WeatherHistoryDto> getWeatherHistory()  {

        log.info("Получен запрос на получение истории просмотра погоды");

        List<WeatherHistoryDto> weatherHistoryDtoList = WeatherMapper.toWeatherHistoryDtoList(weatherService
                .getWeatherHistory());

        log.info("Успешно отправлена история о просмотре погоды");

        return weatherHistoryDtoList;
    }
}
