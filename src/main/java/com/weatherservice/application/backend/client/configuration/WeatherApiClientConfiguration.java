package com.weatherservice.application.backend.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <p>Класс для конфигурации клиента, который получает данные о погоде через API.</p>
 */
@Configuration
public class WeatherApiClientConfiguration {

    /**
     * <p>URL для доступа к сервису API погоды.</p>
     */

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Bean
    public WebClient webClient() {

        return WebClient.create(weatherApiUrl);
    }
}
