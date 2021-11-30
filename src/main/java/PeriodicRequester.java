import javax.jms.JMSException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.TimerTask;

public class PeriodicRequester extends TimerTask {

    private static final String fileName = "BarcelonaWeather";
    private static final String API_KEY = "609f75cfe734832b9bf4cbd6ffb343eb";
    private static final String Location = "Barcelona";
    private static final String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + Location + "&units=metric&appid=" + API_KEY;

    public void run() {
        try {
            WeatherEvent weatherEvent = responseToEvent(requestWeather());
            WeatherEventHandler handler = new WeatherEventHandler(weatherEvent);
            handler.storeToFile(fileName);// practica 1
            handler.sendToBroker();// practica 2

        } catch (IOException | InterruptedException | JMSException e) {
            e.printStackTrace();
        }
    }


    public static WeatherResponse.Root requestWeather() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Serializer.deserializeWeatherResponse(response.body());
    }

    private static WeatherEvent responseToEvent(WeatherResponse.Root response) {
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
}
