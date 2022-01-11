import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TemperaturePredictor implements Predictor {
    private final String DATASET_CSV_PATH = "/tmp/weather_training_dataset.csv";
    private final String SCRIPT_PATH = "analytics/src/main/python/linear_regression.py"; // edit here to

    @Override
    public void trainAndPredict(ResultSet data) {
        createTrainingCSV(data);
        runPython();
    }

    private void createTrainingCSV(ResultSet resultSet) {
        try {
            CSVWriter cw = new CSVWriter(new FileWriter(DATASET_CSV_PATH));
            int i = 0;
            while (resultSet.next()) {
                cw.writeNext(new String[]{String.valueOf(resultSet.getLong("ts")), String.valueOf(resultSet.getDouble("temperature"))});
            }
            cw.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void runPython() {
        long quarterPrediction = LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.UTC).toEpochMilli() / 1000;
        long threeHourPrediction = LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.UTC).toEpochMilli() / 1000;
        long dayPrediction = LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli() / 1000;
        try {
            Process process = Runtime.getRuntime().exec(
                    "python3" + " " +
                            SCRIPT_PATH + " " +
                            DATASET_CSV_PATH + " " +
                            quarterPrediction + " " +
                            threeHourPrediction + " " +
                            dayPrediction + " "
            );
            int exitcode = process.waitFor();
            if (exitcode == 0) {
                BufferedReader out = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = out.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (
                IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
