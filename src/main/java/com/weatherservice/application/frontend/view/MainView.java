package com.weatherservice.application.frontend.view;

import com.vaadin.flow.component.notification.Notification;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Route("")
public final class MainView extends VerticalLayout {

    private final WeatherServiceClient weatherServiceClient;
    private final TextField latitudeField = new TextField("Широта");
    private final TextField longitudeField = new TextField("Долгота");
    private final H1 weatherTitle = new H1();
    private final H2 weatherDetails = new H2();
    private final Image weatherImage = new Image();
    private final Grid<WeatherHistoryDto> historyGrid = new Grid<>(WeatherHistoryDto.class);
    private final Button getWeatherButton = new Button("Получить погоду");

    public MainView(WeatherServiceClient weatherServiceClient) {
        this.weatherServiceClient = weatherServiceClient;

        setAlignItems(Alignment.CENTER);
        configureFields();
        configureButton();

        historyGrid.setColumns("latitude", "longitude", "weather", "queryDate");
        historyGrid.getColumnByKey("latitude").setHeader("Широта");
        historyGrid.getColumnByKey("longitude").setHeader("Долгота");
        historyGrid.getColumnByKey("weather").setHeader("Погода");
        historyGrid.getColumnByKey("queryDate").setHeader("Дата просмотра погоды");

        H3 historyTitle = new H3("История просмотра погоды");

        add(latitudeField, longitudeField, getWeatherButton, weatherTitle, weatherDetails, weatherImage,
                historyTitle, historyGrid);

        updateValidationState();

        fetchWeatherHistory();
    }

    private void configureFields() {
        latitudeField.setPlaceholder("От -90 до 90");
        longitudeField.setPlaceholder("От -180 до 180");

        latitudeField.setValueChangeMode(ValueChangeMode.EAGER);
        longitudeField.setValueChangeMode(ValueChangeMode.EAGER);

        latitudeField.addValueChangeListener(event ->
                updateValidationState());
        longitudeField.addValueChangeListener(event ->
                updateValidationState());

        updateValidationState();
    }

    private void configureButton() {
        getWeatherButton.addClickListener(event -> {
            double latitude = Double.parseDouble(latitudeField.getValue());
            double longitude = Double.parseDouble(longitudeField.getValue());
            fetchWeather(latitude, longitude);
        });
    }

    private void updateValidationState() {
        boolean isLatValid = validateCoordinate(latitudeField, -90, 90);
        boolean isLonValid = validateCoordinate(longitudeField, -180, 180);
        getWeatherButton.setEnabled(isLatValid && isLonValid);
    }

    private boolean validateCoordinate(TextField field, int min, int max) {
        String value = field.getValue();

        if (value == null || value.trim().isEmpty()) {
            field.setInvalid(false);
            field.setErrorMessage("");
            return false;
        }

        try {
            double number = Double.parseDouble(value);
            if (number >= min && number <= max) {
                field.setInvalid(false);
                field.setErrorMessage("");
                return true;
            }
        } catch (NumberFormatException ignored) {}

        field.setInvalid(true);
        field.setErrorMessage("Введите корректное значение в диапазоне " + min + " до " + max);
        return false;
    }

    private void fetchWeather(double latitude, double longitude) {
        try {
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
        } catch (WebClientResponseException.Unauthorized e) {
            Notification notification = new Notification(
                    "Ошибка авторизации на внешнем сервисе погоды, неправильный API ключ доступа.");
            notification.setPosition(Notification.Position.MIDDLE);
            notification.setDuration(5000);
            notification.open();
        }
    }

    private void fetchWeatherHistory() {
        List<WeatherHistoryDto> history = weatherServiceClient.getWeather().block();

        if (history != null) {
            historyGrid.setItems(history);
        }
    }
}
