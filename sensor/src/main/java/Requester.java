import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requester {
    private static final String API_KEY = "609f75cfe734832b9bf4cbd6ffb343eb";
    private static final String LOCATION = "Barcelona";
    private static final String URL_STRING = "https://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&units=metric&appid=" + API_KEY;

    public WeatherResponse.Root requestWeather() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_STRING))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return WeatherResponse.deserialize(response.body());
    }

}
