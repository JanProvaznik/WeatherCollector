import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TemperaturePredictor implements Predictor {
    private final String DATASET_CSV_PATH = "/tmp/weather_training_dataset.csv";
    private final String SCRIPT_PATH = "linear_regression.py"; // edit here to

    @Override
    public void trainAndPredict(ResultSet data) {
        createTrainingCSV(data);
        runPython();
    }

    private void createTrainingCSV(ResultSet resultSet) {
        try {
            String[] timestamps = (String[]) resultSet.getArray("ts").getArray();
            String[] temperatures = (String[]) resultSet.getArray("temperature").getArray();
            CSVWriter cw = new CSVWriter(new FileWriter(DATASET_CSV_PATH));
            for (int i = 0; i < timestamps.length; i++) {
                cw.writeNext(new String[]{timestamps[i], temperatures[i]});
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void runPython() {
        ProcessBuilder pb = new ProcessBuilder(
                "python3",
                SCRIPT_PATH,
                DATASET_CSV_PATH,
                Long.toString(LocalDateTime.now().plusMinutes(15).toInstant((ZoneOffset) ZoneOffset.systemDefault()).toEpochMilli()),//todo check
                Long.toString(LocalDateTime.now().plusHours(3).toInstant((ZoneOffset) ZoneOffset.systemDefault()).toEpochMilli()),//todo check
                Long.toString(LocalDateTime.now().plusDays(1).toInstant((ZoneOffset) ZoneOffset.systemDefault()).toEpochMilli())//todo check
        );
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
