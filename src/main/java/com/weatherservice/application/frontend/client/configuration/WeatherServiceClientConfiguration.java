package com.weatherservice.application.frontend.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <p>Класс для конфигурации клиента, который получает данные о погоде из сервиса погоды.</p>
 */
@Configuration
public class WeatherServiceClientConfiguration {

    /**
     * <p>URL для доступа к сервису погоды.</p>
     */
    @Value("${weather.service.url}")
    private String serviceUrl;

    @Bean(name = "weatherServiceWebClient")
    public WebClient webClient() {

        return WebClient.create(serviceUrl);
    }
}
