import javax.jms.JMSException;
import java.io.IOException;
import java.util.TimerTask;

public class Sensor extends TimerTask {

    private MessageSender sender;
    private Requester requester;

    public Sensor() throws JMSException {
        sender = new MessageSender();
        requester = new Requester();
    }

    public void run() {
        try {
            WeatherEvent event = WeatherEventBuilder.buildEventFromResponse(requester.requestWeather());
            sender.sendString(event.serialize());
        } catch (IOException | InterruptedException | JMSException e) {
            e.printStackTrace();
        }
    }


}
