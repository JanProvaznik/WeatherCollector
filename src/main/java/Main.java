import java.io.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


class Requester extends TimerTask{

    public void run(){
      requestandstore();
    }
    void requestandstore(){
        try {
            Map weatherJson = sendRequest();
            store(weatherJson);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    void store(Map<String,Object> weather) throws IOException {


        String fileName="BarcelonaWeather";
        FileWriter fw = new FileWriter(fileName+LocalDateTime.now().getHour(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        String weatherState = weather.get("weather").toString().split(", ")[1].split("=")[1];

        bw.write(LocalDateTime.now().toString()+","+weatherState);

        bw.newLine();
        bw.close();
    }
    public static Map<String,Object> jsonToMap(String str){
        return new Gson().fromJson(str,new
                TypeToken<HashMap<String,Object>>() {}.getType());
    }

    public static Map<String, Object> sendRequest() throws IOException, InterruptedException {
        String API_KEY = "609f75cfe734832b9bf4cbd6ffb343eb";
        String Location = "Barcelona";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+Location+"&appid="+API_KEY;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return jsonToMap(response.body());
    }
}

public class Main {

    public static void main(String[] args) {
        String json = "{\"coord\":{\"lon\":2.159,\"lat\":41.3888},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"base\":\"stations\",\"main\":{\"temp\":286.45,\"feels_like\":286.03,\"temp_min\":284.37,\"temp_max\":288.01,\"pressure\":1008,\"humidity\":84},\"visibility\":10000,\"wind\":{\"speed\":3.58,\"deg\":140,\"gust\":8.94},\"rain\":{\"1h\":0.13},\"clouds\":{\"all\":75},\"dt\":1637757906,\"sys\":{\"type\":2,\"id\":18549,\"country\":\"ES\",\"sunrise\":1637736636,\"sunset\":1637771157},\"timezone\":3600,\"id\":3128760,\"name\":\"Barcelona\",\"cod\":200}";
        WeatherResponse.Root r = new Gson().fromJson(json, WeatherResponse.Root.class);
        Requester periodicWeather = new Requester();
//        periodicWeather.run();
//        Timer timer = new Timer(false);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(periodicWeather,0, 15, TimeUnit.MINUTES);//15 minutes
    }
}