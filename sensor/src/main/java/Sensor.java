import org.apache.activemq.ActiveMQConnection;

import java.io.IOException;
import java.util.TimerTask;

public class Sensor extends TimerTask {
    private final String API_KEY = "609f75cfe734832b9bf4cbd6ffb343eb";
    private final String LOCATION = "Barcelona";

    private final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private final String TOPIC_SUBJECT = "sensor.Weather";

    private StringSender sender;
    private WeatherRequester requester;

    public Sensor() {
        requester = new WeatherRequester(API_KEY, LOCATION);
        sender = new ActiveMQSender(BROKER_URL, TOPIC_SUBJECT);
    }

    public void run() {
        try {
            WeatherEvent event = WeatherEventBuilder.buildEventFromResponse(requester.requestWeather());
            sender.sendString(event.serialize());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
