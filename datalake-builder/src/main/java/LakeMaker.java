import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LakeMaker implements MessageListener {
    private final String SENSOR_WEATHER_PATH = "datalake/events/sensor.Weather";
    private String datalakePath;

    public LakeMaker(String path) {
        datalakePath = path;
        createDirectories();
    }

    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            storeToFile(tm.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void createDirectories() {
        new File(datalakePath + "/" + SENSOR_WEATHER_PATH).mkdirs();
    }

    private void storeToFile(String message) throws IOException {
        String ts = new Gson().fromJson(message, WeatherEvent.class).getTs();
        ZonedDateTime dateTime = Instant.parse(ts).atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String dateAndHour = dateTime.format(formatter);

        String fileName = new StringBuilder()
                .append(datalakePath)
                .append("/")
                .append(SENSOR_WEATHER_PATH)
                .append("/")
                .append(dateAndHour)
                .append(".json")
                .toString();


        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(message);
        bw.newLine();
        bw.close();
    }
}
