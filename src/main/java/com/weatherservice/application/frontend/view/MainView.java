package com.weatherservice.application.frontend.view;

import com.weatherservice.application.backend.dto.WeatherDto;
import com.weatherservice.application.backend.dto.WeatherHistoryDto;
import com.weatherservice.application.frontend.client.WeatherServiceClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final WeatherServiceClient weatherServiceClient;

    private final TextField latitudeField = new TextField("Широта");
    private final TextField longitudeField = new TextField("Долгота");
    private final H1 weatherTitle = new H1();
    private final H2 weatherDetails = new H2();
    private final Image weatherImage = new Image();
    private final Grid<WeatherHistoryDto> historyGrid = new Grid<>(WeatherHistoryDto.class);

    public MainView(WeatherServiceClient weatherServiceClient) {
        this.weatherServiceClient = weatherServiceClient;

        setAlignItems(Alignment.CENTER);

        Button getWeatherButton = getButton();

        historyGrid.setColumns("latitude", "longitude", "weather", "queryDate");
        historyGrid.getColumnByKey("latitude").setHeader("Широта");
        historyGrid.getColumnByKey("longitude").setHeader("Долгота");
        historyGrid.getColumnByKey("weather").setHeader("Погода");
        historyGrid.getColumnByKey("queryDate").setHeader("Дата просмотра погоды");
        H3 historyTitle = new H3("История просмотра погоды");
        add(latitudeField, longitudeField, getWeatherButton, weatherTitle, weatherDetails, weatherImage, historyTitle,
                historyGrid);
        fetchWeatherHistory();
    }

    private Button getButton() {
        Button getWeatherButton = new Button("Получить погоду");
        getWeatherButton.addClickListener(event -> {
            try {
                double latitude = Double.parseDouble(latitudeField.getValue());
                double longitude = Double.parseDouble(longitudeField.getValue());
                fetchWeather(latitude, longitude);
            } catch (NumberFormatException e) {
                weatherTitle.setText("Ошибка: неверный формат координат.");
            }
        });
        return getWeatherButton;
    }

    private void fetchWeather(double latitude, double longitude) {
        WeatherDto weather = weatherServiceClient.getWeather(latitude, longitude).block();

        if (weather != null) {
            weatherTitle.setText(weather.getWeather());
            weatherDetails.setText(String.format("Температура: %.0f градусов, ощущается как %.0f",
                    weather.getTemperature(), weather.getFeelsLike()));
            weatherImage.setSrc(weather.getPhotoUrl());
            fetchWeatherHistory();
        } else {
            weatherTitle.setText("Ошибка при получении данных о погоде.");
        }
    }
    
    private void fetchWeatherHistory() {
        List<WeatherHistoryDto> history = weatherServiceClient.getWeather().block();

        if (history != null) {
            historyGrid.setItems(history);
        }
    }
}