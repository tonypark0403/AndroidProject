package ca.seneca.map524.smartsecretary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.seneca.map524.smartsecretary.R;

/**
 * Created by Wonho on 11/12/2016.
 */
public class ForecastViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageIcon;
    public TextView textDateTime;
    public TextView textDesc;
    public TextView textTemp;

    public ForecastViewHolder(View view) {
        super(view);

        imageIcon = (ImageView)view.findViewById(R.id.imageIcon);
        textDateTime = (TextView)view.findViewById(R.id.textDateTime);
        textDesc = (TextView)view.findViewById(R.id.textDesc);
        textTemp = (TextView)view.findViewById(R.id.textTemp);
    }
}
