import javax.jms.JMSException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {

        try {
        Sensor sensor = new Sensor();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(sensor, 0, 15, TimeUnit.MINUTES);
        } catch (JMSException e) {
            e.printStackTrace();
            
        }
    }
}