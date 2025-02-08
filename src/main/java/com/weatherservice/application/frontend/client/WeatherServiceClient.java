package com.weatherservice.application.frontend.client;

import com.weatherservice.application.backend.dto.WeatherDto;
import com.weatherservice.application.backend.dto.WeatherHistoryDto;
import com.weatherservice.application.backend.model.WeatherHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>Клиент для получения информации о погоде через сервис погоды.</p>
 */
@Component
public class WeatherServiceClient {

    /**
     * <p>Клиент.</p>
     */
    private final WebClient webClient;

    @Autowired
    public WeatherServiceClient(@Qualifier("weatherServiceWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * <p>Получение прогноза погоды по координатам.</p>
     *
     * @param latitude  Широта.
     * @param longitude Долгота.
     *
     * @return Прогноз погоды в виде строки.
     */
    public Mono<WeatherDto> getWeather(double latitude, double longitude) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .build())
                .retrieve()
                .bodyToMono(WeatherDto.class);
    }

    /**
     * <p>Получение истории просмотра прогноза погоды.</p>
     *
     * @return Список моделей истории погоды List<{@link WeatherHistory}>.
     */
    public Mono<List<WeatherHistoryDto>> getWeather() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/history")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
