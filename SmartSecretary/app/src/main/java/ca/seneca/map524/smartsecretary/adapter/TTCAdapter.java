package ca.seneca.map524.smartsecretary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.TTC;

/**
 * Created by Tony on 11/14/2016.
 */

public class TTCAdapter extends BaseAdapter {

    Context context;

    String number;
    ArrayList<String> times;

    TTC ttc;

    public TTCAdapter(Context context, TTC ttc) {
        this.context = context;
        this.ttc = ttc;
    }

    @Override
    public int getCount() {
        return ttc.getTimes().size();
    }

    @Override
    public Object getItem(int i) {
        return times.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.ttc_list_item, null);

        TextView textNumber = (TextView)row.findViewById(R.id.textNumber);
        textNumber.setText(ttc.getName());

        TextView textTime = (TextView)row.findViewById(R.id.textTime);
        textTime.setText(ttc.getTimes().get(i));

        return row;
    }
}
