import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.sql.ResultSet;

public class MessageHandler implements MessageListener {
    private final WeatherDatabase dbManager;
    private final ChartCreator chartCreator;
    private final Predictor predictor;

    public MessageHandler(WeatherDatabase db, ChartCreator chartCreator, Predictor predictor) {
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

            ResultSet chartData = dbManager.selectAll();
            chartCreator.renderChart(chartData);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
