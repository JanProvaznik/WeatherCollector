import javax.jms.JMSException;

public class WeatherEventHandler {
    private final WeatherEvent event;

    public WeatherEventHandler(WeatherEvent event) {
        this.event = event;
    }

    public void sendToBroker() throws JMSException {
        MessageSender.sendStringDefault(event.serialize());
    }
}
