import java.time.Instant;

public class WeatherEventBuilder {
    private final WeatherEvent weatherEvent;

    public WeatherEventBuilder() {
        this.weatherEvent = new WeatherEvent();
    }

    public static WeatherEvent buildEventFromResponse(WeatherResponse.Root response) {
        WeatherEventBuilder weatherEventBuilder = new WeatherEventBuilder();
        return weatherEventBuilder
                .timeStamp(Instant.now().toString())
                .location(response.coord.lat, response.coord.lon)
                .weather(response.weather.get(0).main)
                .temperature(response.main.temp)
                .windSpeed(response.wind.speed)
                .windDirection(response.wind.deg)
                .humidity(response.main.humidity)
                .pressure(response.main.pressure)
                .build();
    }

    public WeatherEventBuilder timeStamp(String ts) {
        weatherEvent.setTs(ts);
        return this;
    }

    public WeatherEventBuilder location(double lat, double lon) {
        weatherEvent.setLocation(new WeatherEvent.Location(lat, lon));
        return this;
    }

    public WeatherEventBuilder weather(String weather) {
        weatherEvent.setWeather(weather);
        return this;
    }

    public WeatherEventBuilder temperature(double temp) {
        weatherEvent.setTemp(temp);
        return this;
    }

    public WeatherEventBuilder windSpeed(double windSpeed) {
        weatherEvent.setWind(windSpeed);
        return this;

    }

    public WeatherEventBuilder windDirection(int windDierection) {
        weatherEvent.setWindDirection(windDierection);
        return this;
    }

    public WeatherEventBuilder humidity(int humidity) {
        weatherEvent.setHumidity(humidity);
        return this;
    }

    public WeatherEventBuilder pressure(int pressure) {
        weatherEvent.setPressure(pressure);
        return this;
    }

    public WeatherEvent build() {
        return weatherEvent;
    }

}
