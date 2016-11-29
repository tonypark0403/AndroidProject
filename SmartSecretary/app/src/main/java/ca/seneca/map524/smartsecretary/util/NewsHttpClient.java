package ca.seneca.map524.smartsecretary.util;

import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Wonho on 11/6/2016.
 */
public class NewsHttpClient extends HttpClient {

    private final static String TAG = "NewsHttpClient";

    /**
     * get news data from CBC
     * @return String
     */
    public InputStreamReader getNewsData(String newsUrl) {

        InputStreamReader result = null;

        try {
            URL url = new URL(newsUrl);
            result = getData(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
