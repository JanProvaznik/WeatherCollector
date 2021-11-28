public class WeatherEvent {
    public String ts;
    public Location location;
    public String weather;
    public double temp;
    public double wind; //speed
    public int windDirection; // in degrees
    public int humidity;
    public int pressure;

    public WeatherEvent(String ts, Location location, String weather, double temp,
                        double wind, int windDirection, int humidity, int pressure) {
        this.ts = ts;
        this.location = location;
        this.weather = weather;
        this.temp = temp;
        this.wind = wind;
        this.windDirection = windDirection;
        this.humidity = humidity;
        this.pressure = pressure;
    }


    public static class Location{
       public double lat;
       public double lon;

        public Location(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }
}

