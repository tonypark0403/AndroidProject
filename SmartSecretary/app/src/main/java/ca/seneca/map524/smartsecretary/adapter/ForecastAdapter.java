package ca.seneca.map524.smartsecretary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.Weather;
import ca.seneca.map524.smartsecretary.model.WeatherIcon;

/**
 * Created by Wonho on 11/12/2016.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    private Context context;
    private List<Weather> weathers;

    public ForecastAdapter(Context context, List<Weather> weathers) {
        this.context = context;
        this.weathers = weathers;
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list_item, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        // image
        holder.imageIcon.setAdjustViewBounds(true);
        holder.imageIcon.setMaxWidth(150);
        holder.imageIcon.setMaxHeight(150);
        WeatherIcon.setForecastImage(holder.imageIcon, weathers.get(position).currentCondition.getWeatherId());

        // text
        holder.textDateTime.setText(weathers.get(position).dateTime.getDateTime().substring(5, 16));
        holder.textDesc.setText(weathers.get(position).currentCondition.getCondition());
        holder.textTemp.setText("" + Math.round((weathers.get(position).temperature.getTemp() - 273.15)) + "\u2103");
    }
}
