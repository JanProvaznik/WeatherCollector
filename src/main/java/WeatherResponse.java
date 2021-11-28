import java.util.List;
//Generated by https://json2csharp.com/json-to-pojo
public class WeatherResponse {
    static class Coord {
        public double lon;
        public double lat;
    }

    static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    static class Main {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }

    static class Wind {
        public double speed;
        public int deg;
        public double gust;
    }

    static class Rain {
        public double _1h;
    }

    static class Clouds {
        public int all;
    }

    static class Sys {
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public static class Root {
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