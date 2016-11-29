package ca.seneca.map524.smartsecretary.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.seneca.map524.smartsecretary.model.TTC;

/**
 * Created by Tony on 11/14/2016.
 */

public class TTCJsonParser {
    public static TTC getTTC(String data) throws JSONException {
        //Log.d("getTTC", "[Begin]");

        TTC ttc = new TTC();

        JSONObject jObj = new JSONObject(data);
        //Log.d("getTTC", "[JSONObject jObj = new JSONObject(data)]");

        JSONArray stopArray = jObj.getJSONArray("stops");
        //Log.d("getTTC", "[JSONArray stopArray = jObj.getJSONArray(\"stop\");]");

        JSONArray routeArray = stopArray.getJSONObject(0).getJSONArray("routes");
        //Log.d("getTTC", "[JSONObject routeObj = jObj.getJSONObject(\"routes\")]");

        String name = routeArray.getJSONObject(0).getString("name");
        //Log.d("getTTC", "[String name = routeObj.getString(\"name\");]");

        ttc.setName(name);

        JSONArray timesArray = routeArray.getJSONObject(0).getJSONArray("stop_times");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < timesArray.length(); i++) {
            list.add(timesArray.getJSONObject(i).getString("departure_time"));
        }
        ttc.setTimes(list);
        //JSONObject stopsObj = routeObj.getJSONObject("stop_times");
        //JSONObject timeObj = stopsObj.getJSONObject("departure_time");

        return ttc;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}