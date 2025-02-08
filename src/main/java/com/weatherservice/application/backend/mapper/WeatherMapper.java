package com.weatherservice.application.backend.mapper;

import com.weatherservice.application.backend.dto.WeatherDto;
import com.weatherservice.application.backend.dto.WeatherHistoryDto;
import com.weatherservice.application.backend.model.WeatherHistory;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.List;

/**
 * <p>Маппер истории погоды {@link WeatherHistoryDto} и {@link WeatherDto}.</p>
 */
public class WeatherMapper {

    /**
     * <p>Преобразует JSON-ответ от API погоды в объект {@link WeatherDto}.</p>
     *
     * <p>Метод извлекает описание погоды, текущую температуру, URl фотографии и ощущаемую температуру
     * из переданного JSON-ответа. Описание погоды форматируется с заглавной буквы.</p>
     *
     * @param jsonResponse Строка в формате JSON, содержащая данные о погоде.
     *
     * @return Dto {@link WeatherDto}.
     *
     * @throws JSONException Если формат JSON некорректен или отсутствуют необходимые поля.
     */
    public static WeatherDto toWeatherDto(String jsonResponse) throws JSONException {

        JSONObject json = new JSONObject(jsonResponse);
        String weather = getWeatherFromJson(json);
        double temperature = getTemperatureFromJson(json, "temp");
        double feelsLike = getTemperatureFromJson(json, "feels_like");
        String photoUrl = getPhotoFromJson(json);

        return WeatherDto.builder()
                .weather(weather)
                .temperature(temperature)
                .feelsLike(feelsLike)
                .photoUrl(photoUrl)
                .build();
    }

    /**
     * <p>Преобразование данных о погоде в объект {@link WeatherHistory}.</p>
     *
     * <p>Создание объекта {@link WeatherHistory}, используя координаты (широту и долготу),
     * дату запроса и информацию о погоде, представленную в объекте {@link WeatherDto}.</p>
     *
     * @param latitude Широта местоположения.
     * @param longitude Долгота местоположения.
     * @param queryDate Дата запроса погоды.
     * @param dto Dto {@link WeatherDto}.
     *
     * @return Модель {@link WeatherHistory}.
     */
    public static WeatherHistory toWeatherHistory(
            double latitude,
            double longitude,
            LocalTime queryDate,
            WeatherDto dto
    ) {

        return WeatherHistory.builder()
                .latitude(latitude)
                .longitude(longitude)
                .weather(dto.getWeather())
                .queryDate(queryDate)
                .build();
    }

    /**
     * <p>Получение списка dto истории просмотра погоды List<{@link WeatherHistoryDto}>.
     *
     * @param historyList Список моделей {@link WeatherHistory}.
     *
     * @return Список dto истории просмотра.
     */
    public static List<WeatherHistoryDto> toWeatherHistoryDtoList(List<WeatherHistory> historyList) {

        return historyList.stream()
                .map(weatherHistory ->
                        WeatherHistoryDto.builder()
                                .latitude(weatherHistory.getLatitude())
                                .longitude(weatherHistory.getLongitude())
                                .weather(weatherHistory.getWeather())
                                .queryDate(weatherHistory.getQueryDate())
                                .build())
                .toList();
    }

    /**
     * <p>Получение температуры погоды.</p>
     *
     * @param json Погода в формате строки.
     * @param filed Поле, которое мы хотим получить из строки.
     *
     * @return Температуру погоды.
     *
     * @throws JSONException Если формат JSON некорректен или отсутствуют необходимые поля.
     */
    private static double getTemperatureFromJson(JSONObject json, String filed) throws JSONException {

        return json
                .getJSONObject("main")
                .getDouble(filed);
    }

    /**
     * <p>Получение фотографии погоды.</p>
     *
     * @param json Погода в формате строки.
     *
     * @return URL фото погоды.
     *
     * @throws JSONException Если формат JSON некорректен или отсутствуют необходимые поля.
     */
    private static String getPhotoFromJson(JSONObject json) throws JSONException {

        String iconCode = json
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("icon");

        return "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
    }

    /**
     * <p>Получение описания погоды.</p>
     *
     * @param json Погода в формате строки.
     *
     * @return Описание погоды.
     *
     * @throws JSONException Если формат JSON некорректен или отсутствуют необходимые поля.
     */
    private static String getWeatherFromJson(JSONObject json) throws JSONException {

       String weather = json
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");
       weather = weather.substring(0, 1).toUpperCase() + weather.substring(1);

       return weather;
    }
}
