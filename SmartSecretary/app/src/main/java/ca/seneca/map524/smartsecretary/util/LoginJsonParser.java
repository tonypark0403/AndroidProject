package ca.seneca.map524.smartsecretary.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.seneca.map524.smartsecretary.model.Login;

/**
 * Created by Tony on 11/14/2016.
 */

public class LoginJsonParser {
    public static List<Login> getLogin(String data) throws JSONException {
        List<Login> login = new ArrayList<>();

        JSONArray jArr = new JSONArray(data);

        JSONObject JSONLogin;
        // We use only the first value
        for(int i = 0; i < jArr.length(); i++){
            JSONLogin = jArr.getJSONObject(i);
            login.add(new Login(JSONLogin.getString("FirstName"), JSONLogin.getString("Password")));
        }
        return login;
    }
}
