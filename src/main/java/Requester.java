import com.google.gson.Gson;

import javax.jms.JMSException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimerTask;

public class Requester extends TimerTask {
    public void run() {
        try {
            WeatherResponse.Root weatherResponse = requestWeather();
            storeToFile(weatherResponse);
            WeatherEvent event = responseToEvent(weatherResponse);
            sendEventToBroker(event);

        } catch (IOException | InterruptedException | JMSException e ) {
            e.printStackTrace();
        }
    }

    private static void sendEventToBroker(WeatherEvent event) throws JMSException {
        MessageSender.sendStringDefault(weatherEventToJson(event));
    }

    private static WeatherEvent responseToEvent(WeatherResponse.Root response) {
        return  new WeatherEvent(
                Instant.now().toString(),
                new WeatherEvent.Location(response.coord.lat,response.coord.lon),
                response.weather.get(0).main,
                response.main.temp,
                response.wind.speed,
                response.wind.deg,
                response.main.humidity,
                response.main.pressure
                );
    }

    private static void storeToFile(WeatherResponse.Root weatherResponse) throws IOException {
        String fileName = "BarcelonaWeather";
        FileWriter fw = new FileWriter(fileName + LocalDateTime.now().getHour(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        String weatherState = weatherResponse.weather.get(0).main;
        double temperature= weatherResponse.main.temp;
        int humidity = weatherResponse.main.humidity;
        bw.write(String.format("%s,%s,%f,%d", Instant.now(), weatherState, temperature, humidity));

        bw.newLine();
        bw.close();
    }

    private static WeatherResponse.Root jsonToClasses(String json) {
        return new Gson().fromJson(json, WeatherResponse.Root.class);
    }

    public static String weatherEventToJson(WeatherEvent event) {
        return new Gson().toJson(event);
    }

    public static WeatherResponse.Root requestWeather() throws IOException, InterruptedException {
        String API_KEY = "609f75cfe734832b9bf4cbd6ffb343eb";
        String Location = "Barcelona";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + Location + "&units=metric&appid=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return jsonToClasses(response.body());
    }
}
