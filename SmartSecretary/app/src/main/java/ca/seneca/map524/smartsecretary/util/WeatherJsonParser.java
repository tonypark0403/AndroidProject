package ca.seneca.map524.smartsecretary.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.seneca.map524.smartsecretary.model.Forecast;
import ca.seneca.map524.smartsecretary.model.Weather;


/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class WeatherJsonParser {

    /**
     * parse weather JSON data
     * @param data
     * @return
     * @throws JSONException
     */
    public static Weather getWeather(String data) throws JSONException {
        Weather weather = new Weather();

        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("weather");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setWeatherId(JSONWeather.getInt("id"));
        weather.currentCondition.setDescr(JSONWeather.getString("description"));
        weather.currentCondition.setCondition(JSONWeather.getString("main"));
        weather.currentCondition.setIcon(JSONWeather.getString("icon"));

        JSONObject mainObj = jObj.getJSONObject("main");
        weather.currentCondition.setHumidity(mainObj.getInt("humidity"));
        weather.currentCondition.setPressure(mainObj.getInt("pressure"));
        weather.temperature.setMaxTemp(mainObj.getDouble("temp_max"));
        weather.temperature.setMinTemp(mainObj.getDouble("temp_min"));
        weather.temperature.setTemp(mainObj.getDouble("temp"));

        // Wind
        JSONObject windObj = jObj.getJSONObject("wind");
        weather.wind.setSpeed(windObj.getDouble("speed"));
        weather.wind.setDeg(windObj.getDouble("deg"));

        // Clouds
        JSONObject cloudsObj = jObj.getJSONObject("clouds");
        weather.clouds.setPerc(cloudsObj.getInt("all"));

        return weather;
    }

    /**
     * parse forecast JSON data
     * @param data
     * @return
     * @throws JSONException
     */
    public static Forecast getForecast(String data) throws JSONException {

        Forecast forecast = new Forecast();
        JSONObject rootObj = new JSONObject(data);

        // list
        JSONArray listArr = rootObj.getJSONArray("list");
        for (int i = 0; i < Forecast.FORECAST_COUNT; i++) {

            JSONObject listObj = listArr.getJSONObject(i);

            // parse weather
            Weather weather = getWeather(listObj.toString());

            // date and time
            weather.dateTime.setDateTime(listObj.getString("dt_txt"));

            // set weather to forecast
            forecast.addWeather(weather);
        }

        return forecast;
    }
}
