import java.util.List;
public class WeatherResponse {
    class Coord {
        public double lon;
        public double lat;
    }

    class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    class Main {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }

    class Wind {
        public double speed;
        public int deg;
        public double gust;
    }

    class Rain {
        public double _1h;
    }

    class Clouds {
        public int all;
    }

    class Sys {
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public class Root {
        public Coord coord;
        public List<Weather> weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Rain rain;
        public Clouds clouds;
        public int dt;
        public Sys sys;
        public int timezone;
        public int id;
        public String name;
        public int cod;
    }
}