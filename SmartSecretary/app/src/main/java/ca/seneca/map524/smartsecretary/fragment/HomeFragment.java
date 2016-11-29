package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.Weather;
import ca.seneca.map524.smartsecretary.model.WeatherIcon;
import ca.seneca.map524.smartsecretary.util.WeatherJsonParser;
import ca.seneca.map524.smartsecretary.util.WeatherHttpClient;

/**
 * Created by Wonho on 10/27/2016.
 */
public class HomeFragment extends Fragment {

    private final static String TAG = "HomeFragment";

    // weather
    ImageView mImageWeather;
    TextView mTextWeather;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // current date and time
        TextView textDate = (TextView)rootView.findViewById(R.id.textDate);
        TextView textTime = (TextView)rootView.findViewById(R.id.textTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String time = timeFormat.format(Calendar.getInstance().getTime());

        textDate.setText(date);
        textTime.setText(time);

        // weather image
        mImageWeather = (ImageView)rootView.findViewById(R.id.imageWeather);
        mImageWeather.setAdjustViewBounds(true);
        mImageWeather.setMaxWidth(250);
        mImageWeather.setMaxHeight(250);

        // weather description
        mTextWeather = (TextView)rootView.findViewById(R.id.textWeather);

        // excute WeatherTask to get weather data
        new WeatherTask().execute("toronto");

        return rootView;
    }

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

            WeatherIcon.setWeatherImage(mImageWeather, weather.currentCondition.getWeatherId());
            mTextWeather.setText(weather.currentCondition.getCondition());
        }
    }
}
