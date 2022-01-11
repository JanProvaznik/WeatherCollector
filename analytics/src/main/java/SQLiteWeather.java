import java.sql.*;
import java.time.Instant;

public class SQLiteWeather implements WeatherDatabase {
    private static final String CREATION_SQL = "CREATE TABLE IF NOT EXISTS weather  (ts integer PRIMARY KEY,lat real,lon real,temperature real,pressure real,humidity real);";
    private static final String INSERTION_SQL = "INSERT OR IGNORE INTO weather(ts,lat,lon,temperature,pressure,humidity) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM weather";

    private Connection connection;

    public SQLiteWeather(String dbPath) {
        String url = "jdbc:sqlite:" + dbPath;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createWeatherTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(CREATION_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertWeatherEvent(WeatherEvent event) {
        PreparedStatement pStatement;
        try {
            pStatement = connection.prepareStatement(INSERTION_SQL);
            pStatement.setLong(1, Instant.parse(event.getTs()).toEpochMilli());
            pStatement.setDouble(2, event.getLocation().lat);
            pStatement.setDouble(3, event.getLocation().lon);
            pStatement.setDouble(4, event.getTemp());
            pStatement.setDouble(5, event.getPressure());
            pStatement.setDouble(6, event.getHumidity());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet selectAll() {
        try {
            return connection.prepareStatement(SELECT_ALL_SQL).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
