package com.weatherservice.application.backend.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * <p>Клиент для получения информации о погоде через сторонний API.</p>
 */
@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    /**
     * <p>Клиент.</p>
     */
    private final WebClient webClient;

    /**
     * <p>Ключ API для доступа к сервису погоды.</p>
     */
    @Value("${weather.api.key}")
    private String apiKey;

    /**
     * <p>Получение прогноза погоды по координатам.</p>
     *
     * @param latitude  Широта.
     * @param longitude Долгота.
     *
     * @return Прогноз погоды в виде строки.
     */
    public Mono<String> getWeather(double latitude, double longitude) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .queryParam("lang", "ru")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
