import javax.jms.JMSException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

public class WeatherEventHandler {
    private final WeatherEvent event;

    public WeatherEventHandler(WeatherEvent event) {
        this.event = event;
    }

    public void sendToBroker() throws JMSException {
        MessageSender.sendStringDefault(Serializer.serializeWeatherEvent(event));
    }

    public void storeToFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName + LocalDateTime.now().getHour(), true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(String.format("%s,%s,%f,%d",
                Instant.now(),
                event.getWeather(),
                event.getTemp(),
                event.getHumidity()
        ));
        bw.newLine();
        bw.close();
    }
}
