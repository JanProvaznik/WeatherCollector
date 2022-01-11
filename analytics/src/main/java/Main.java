import java.io.File;
import java.io.IOException;

public class Main {

    private static final String ID = "2";
    private static final String TOPIC_NAME = "sensor.Weather";
    private static final String SUBSCRIPTION_NAME = "analytics";

    private static final String DB_NAME = "database.db";

    public static void main(String[] args) {
        if (!argsValid(args))
            throw new IllegalArgumentException("There must be exactly one parameter: directory path for database");
        String dbPath = args[0] + "/" + DB_NAME;
        try {
            SQLiteWeather db = new SQLiteWeather(dbPath);
            MessageReceiver receiver = new MessageReceiver(ID, TOPIC_NAME, SUBSCRIPTION_NAME);
            ChartCreator chartCreator = new ChartCreator();
            Predictor predictor = new TemperaturePredictor();
            MessageHandler handler = new MessageHandler(db, chartCreator, predictor);
            db.createWeatherTable();
            receiver.setListener(handler);
            receiver.start();

            predictor.trainAndPredict(db.selectAll());
            System.out.println("Press enter to end.");
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean argsValid(String[] args) {
        if (args.length != 1)
            return false;
        return new File(args[0]).isDirectory();
    }

}
