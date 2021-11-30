import com.google.gson.Gson;

public class Serializer {
    public static WeatherResponse.Root deserializeWeatherResponse(String json) {
        return new Gson().fromJson(json, WeatherResponse.Root.class);
    }

    public static String serializeWeatherEvent(WeatherEvent event) {
        return new Gson().toJson(event);
    }
}
