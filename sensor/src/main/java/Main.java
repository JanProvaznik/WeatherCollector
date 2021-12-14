import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {
        PeriodicRequester periodicWeather = new PeriodicRequester();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(periodicWeather, 0, 15, TimeUnit.MINUTES);//15 minutes
    }
}