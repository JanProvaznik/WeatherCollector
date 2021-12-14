import javax.jms.JMSException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.TimerTask;

public class PeriodicRequester extends TimerTask {

    private static final String API_KEY = "609f75cfe734832b9bf4cbd6ffb343eb";
    private static final String Location = "Barcelona";
    private static final String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + Location + "&units=metric&appid=" + API_KEY;

    public void run() {
        try {
            WeatherEvent event = WeatherEventBuilder.buildEventFromResponse(requestWeather());
            WeatherEventHandler handler = new WeatherEventHandler(event);
            handler.sendToBroker();// practica 2

        } catch (IOException | InterruptedException | JMSException e) {
            e.printStackTrace();
        }
    }


    public WeatherResponse.Root requestWeather() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return WeatherResponse.deserialize(response.body());
    }

}
