import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageHandler implements MessageListener {
    private SQLiteWeather dbManager;
    private ChartCreator chartCreator;
    private Predictor predictor;

    public MessageHandler(SQLiteWeather db, ChartCreator chartCreator, Predictor predictor) {
        this.dbManager = db;
        this.chartCreator = chartCreator;
        this.predictor = predictor;
    }

    @Override
    public void onMessage(Message message) {
        try {
            String json = ((TextMessage) message).getText();
            WeatherEvent weatherEvent = new Gson().fromJson(json, WeatherEvent.class);
            dbManager.insertWeatherEvent(weatherEvent);

            ResultSet predictData = dbManager.selectAll();
            predictor.trainAndPredict(predictData);

            ResultSet chartData = predictData;
            chartCreator.renderChart(chartData);
        } catch (JMSException | SQLException e) {
            e.printStackTrace();
        }
    }
}
