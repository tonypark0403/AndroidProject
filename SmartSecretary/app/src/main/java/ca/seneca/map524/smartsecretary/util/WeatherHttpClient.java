package ca.seneca.map524.smartsecretary.util;

import java.net.URL;

import ca.seneca.map524.smartsecretary.model.Forecast;

/**
 * Created by Wonho on 10/27/2016.
 */
public class WeatherHttpClient extends HttpClient {

    private final static String TAG = "WeatherHttpClient";

    // current weather: http://api.openweathermap.org/data/2.5/weather?q=toronto&APPID=e1048834937d5dbd0e842d193d14d031
    // 5 day / 3 hour: http://api.openweathermap.org/data/2.5/forecast?q=toronto&APPID=e1048834937d5dbd0e842d193d14d031
    private final static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private final static String API_KEY = "&APPID=e1048834937d5dbd0e842d193d14d031";

    /**
     * get weather data using OpenWeatherMap API
     * @param location
     * @return String
     */
    public String getWeatherData(String location) {

        String result = null;

        try {
            URL url = new URL(WEATHER_URL + location + API_KEY);
            result = getDataString(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * get forecast data using OpenWeatherMap API
     * @param location
     * @return
     */
    public String getForecastData(String location) {

        String result = null;

        try {
            URL url = new URL(FORECAST_URL + location + "&cnt=" + Forecast.FORECAST_COUNT + API_KEY);
            result = getDataString(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
