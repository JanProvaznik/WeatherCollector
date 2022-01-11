import java.sql.ResultSet;

public interface WeatherDatabase {
    void createWeatherTable();

    void insertWeatherEvent(WeatherEvent event);

    ResultSet selectAll();

}
