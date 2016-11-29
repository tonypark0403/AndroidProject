package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.adapter.ForecastAdapter;
import ca.seneca.map524.smartsecretary.model.Forecast;
import ca.seneca.map524.smartsecretary.model.Weather;
import ca.seneca.map524.smartsecretary.model.WeatherIcon;
import ca.seneca.map524.smartsecretary.util.WeatherJsonParser;
import ca.seneca.map524.smartsecretary.util.WeatherHttpClient;

/**
 * Created by Wonho on 10/27/2016.
 */
public class WeatherFragment extends Fragment {

    private final static String TAG = "WeatherFragment";

    // weather
    TextView mTextCity;
    TextView mTextDesc;
    TextView mTextTemp;
    TextView mTextHumidity;
    TextView mTextPress;
    TextView mTextWind;
    ImageView mImageIcon;

    // forecast
    RecyclerView mRecylerForecast;
    View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        // city
        TextView textCity = (TextView)rootView.findViewById(R.id.textCity);
        textCity.setText("Toronto");

        // weather image
        mImageIcon = (ImageView)rootView.findViewById(R.id.imageIcon);
        // resize
        mImageIcon.setAdjustViewBounds(true);
        mImageIcon.setMaxWidth(300);
        mImageIcon.setMaxHeight(300);

        // TextViews for weather
        mTextCity = (TextView)rootView.findViewById(R.id.textCity);
        mTextDesc = (TextView)rootView.findViewById(R.id.textDesc);
        mTextTemp = (TextView)rootView.findViewById(R.id.textTemp);
        mTextHumidity = (TextView)rootView.findViewById(R.id.textHumidity);
        mTextPress = (TextView)rootView.findViewById(R.id.textPress);
        mTextWind = (TextView)rootView.findViewById(R.id.textWind);

        // forecast: RecylerView for horizontal custom ListView
        mRecylerForecast = (RecyclerView)rootView.findViewById(R.id.recyclerView);

        // layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecylerForecast.setLayoutManager(layoutManager);
        mRootView = rootView;

        // get weather and forecast data
        new WeatherTask().execute("toronto");
        new ForecastTask().execute("toronto");

        return rootView;
    }

    /**
     * AsyncTask to get weather data using OpenWeatherMap API
     */
    class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... strings) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));

            try {
                weather = WeatherJsonParser.getWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            //Toast.makeText(getActivity().getApplicationContext(), "onPostExcute", Toast.LENGTH_SHORT).show();

            int weatherId = weather.currentCondition.getWeatherId();
            WeatherIcon.setWeatherImage(mImageIcon, weatherId);

            //mTextCity.setText(weather.location.getCity() + "," + weather.location.getCountry());
            mTextDesc.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            mTextTemp.setText("Temperature: " + Math.round((weather.temperature.getTemp() - 273.15)) + "\u2103"); // \u2103 = ℃
            mTextHumidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            mTextPress.setText("Pressure: " + weather.currentCondition.getPressure() + " hPa");
            mTextWind.setText("Wind: " + weather.wind.getSpeed() + " m/s " + weather.wind.getDeg() + "\u00b0");  // \u00b0 = °
        }
    }

    /**
     * AsyncTask to get forecast data using OpenWeatherMap API
     */
    public class ForecastTask extends AsyncTask<String, Void, Forecast> {
        @Override
        protected Forecast doInBackground(String... strings) {
            Forecast forecast = null;
            String data = ((new WeatherHttpClient()).getForecastData(strings[0]));

            try {
                forecast = WeatherJsonParser.getForecast(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return forecast;
        }

        @Override
        protected void onPostExecute(Forecast forecast) {
            super.onPostExecute(forecast);

            // set adapter with forecast
            ForecastAdapter adapter = new ForecastAdapter(mRootView.getContext(), forecast.getWeatherList());
            mRecylerForecast.setAdapter(adapter);
        }
    }
}
