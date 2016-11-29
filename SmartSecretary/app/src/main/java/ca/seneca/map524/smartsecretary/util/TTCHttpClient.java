package ca.seneca.map524.smartsecretary.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tony on 11/14/2016.
 */

public class TTCHttpClient {
    private static String BASE_URL = "https://myttc.ca/";

    public String getTTCData(String station) {
        URL url = null;
        String result = null;
        HttpURLConnection conn = null;

        try {
            url = new URL(BASE_URL + station.replace(' ', '_') + ".json");

            conn = (HttpURLConnection)url.openConnection();
            if (conn == null) {
                return null;
            }

            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setConnectTimeout(10000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = null;

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + '\n');
                }

                br.close();

                result = sb.toString();
            } else {
                result = conn.getResponseCode() + " - " + conn.getResponseMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            conn.disconnect();
        }

        return result;
    }
}
