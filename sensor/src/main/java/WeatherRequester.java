import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherRequester {
    private final String url;

    public WeatherRequester(String APIKey, String location) {
        url = new StringBuilder()
                .append("https://api.openweathermap.org/data/2.5/weather?q=")
                .append(location)
                .append("&units=metric&appid=")
                .append(APIKey)
                .toString();
    }

    public WeatherResponse.Root requestWeather() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return WeatherResponse.deserialize(response.body());
    }

}
