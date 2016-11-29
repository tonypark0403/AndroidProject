package ca.seneca.map524.smartsecretary.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wonho on 10/26/2016.
 */
public class HttpClient {

    private final static String TAG = "HttpClient";

    /**
     * http request to get data from url (InputStreamReader)
     * @param url
     * @return InputStreamReader
     */
    public InputStreamReader getData(URL url) {
        HttpURLConnection conn = null;
        InputStreamReader in = null;

        try {
            conn = (HttpURLConnection)url.openConnection();
            if (conn == null) {
                Log.d(TAG, "[getData] HttpURLConnection.openConnection() return null");
                return null;
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new InputStreamReader(conn.getInputStream());
            } else {
                Log.d(TAG, conn.getResponseCode() + " : " + conn.getResponseMessage());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return in;
    }

    /**
     * http request to get data from url (String)
     * @param url
     * @return
     */
    public String getDataString(URL url) {
        InputStreamReader in = getData(url);
        if (in == null) {
            Log.d(TAG, "[getDataString] getData() return null");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(in);
        String result = null;

        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line + '\n');
            }
            br.close();

            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
