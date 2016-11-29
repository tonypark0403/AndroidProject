package ca.seneca.map524.smartsecretary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wonho on 11/12/2016.
 */
public class Forecast {
    public static final int FORECAST_COUNT = 8;
    private List<Weather> weathers = new ArrayList<Weather>();

    public Weather getWeather(int index) {
        if (index < 0 || index >= weathers.size()) {
            return null;
        }

        return weathers.get(index);
    }

    public void addWeather(Weather weather) {

        weathers.add(weather);
    }

    public List<Weather> getWeatherList() {
        return weathers;
    }
}
