package ca.seneca.map524.smartsecretary.util;

import android.util.Log;

import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Tony on 11/14/2016.
 */

public class LoginHttpClient extends HttpClient {
    private final static String TAG = "LoginHttpClient";

    /**
     * get news data from CBC
     * @return String
     */
    public String getLoginData(String loginUrl) {

        Log.d(TAG, "[getLoginData]");

        String result = null;

        try {
            URL url = new URL(loginUrl);
            result = getDataString(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}