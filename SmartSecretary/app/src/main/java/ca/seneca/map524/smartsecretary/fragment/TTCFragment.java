package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.adapter.TTCAdapter;
import ca.seneca.map524.smartsecretary.model.TTC;
import ca.seneca.map524.smartsecretary.util.TTCHttpClient;
import ca.seneca.map524.smartsecretary.util.TTCJsonParser;

/**
 * Created by Tony on 11/12/2016.
 */

public class TTCFragment extends Fragment {

    private final static String TAG = "TTCFragment";

    View rootView;
    ListView mListView;
    TextView mTextTitle;
    String mBusStop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ttc, container, false);

        mListView = (ListView)rootView.findViewById(R.id.listView);
        mTextTitle = (TextView)rootView.findViewById(R.id.textTitle);


        // run AsyncTask to get TTC information
        if (mBusStop != null) {
            new JSONTTCTask().execute(mBusStop);
            mTextTitle.setText(mBusStop.toUpperCase());
        } else {
            new JSONTTCTask().execute("york university");
        }

        return rootView;
    }

    public void setBusStop(String busStop) {
        mBusStop = busStop;
    }

    private class JSONTTCTask extends AsyncTask<String, Void, TTC> {
        @Override
        protected TTC doInBackground(String... strings) {
            String data = ((new TTCHttpClient()).getTTCData(strings[0]));
            TTC ttc = new TTC();

            try {
                ttc = TTCJsonParser.getTTC(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ttc;
        }

        @Override
        protected void onPostExecute(TTC t) {
            super.onPostExecute(t);
            //Log.d("onPostExcute", t.toString());

            try {
                TTCAdapter myTTCAdapter = new TTCAdapter(rootView.getContext(), t);

                // connect the ListView with myCustomAdapter
                mListView.setAdapter(myTTCAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
