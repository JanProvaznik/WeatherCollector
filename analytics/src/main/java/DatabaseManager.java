import com.google.gson.Gson;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

public class DatabaseManager implements MessageListener {
    private Connection connection;
    private static final String CREATION_SQL = "CREATE TABLE weather (ts integer PRIMARY KEY,lat real,lon real,temperature real,pressure real,humidity real);";
    private static final String INSERTION_SQL = "INSERT INTO weather(ts,lat,lon,temperature,pressure,humidity) VALUES(?,?,?,?,?,?)";
    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        if (connection != null) {
            Statement stmt = connection.createStatement();
            stmt.execute(CREATION_SQL);
        }
    }


    private void insertEvent(WeatherEvent event) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERTION_SQL);
        pstmt.setLong(1, Instant.parse(event.getTs()).toEpochMilli());//todo: check if correct
        pstmt.setDouble(2, event.getLocation().lat);
        pstmt.setDouble(3, event.getLocation().lon);
        pstmt.setDouble(4, event.getTemp());
        pstmt.setDouble(5, event.getPressure());
        pstmt.setDouble(6, event.getHumidity());
        pstmt.executeUpdate();
    }

    @Override
    public void onMessage(Message message) {
        try {
            String json = ((TextMessage) message).getText();
            WeatherEvent weatherEvent = new Gson().fromJson(json, WeatherEvent.class);
            insertEvent(weatherEvent);
            ChartCreator.updateGraphics(selectChartData());
        } catch (JMSException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String selectChartData() {
        return null;
    }
}
