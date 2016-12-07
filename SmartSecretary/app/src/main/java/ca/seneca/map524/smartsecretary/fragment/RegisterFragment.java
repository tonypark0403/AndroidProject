package ca.seneca.map524.smartsecretary.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.User;

//server
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//http
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Tony on 11/14/2016.
 */

public class RegisterFragment extends Fragment {
    private final static String TAG = "RegisterFragment";

    View rootView;

    Button createButton;
    LoginFragment mLoginFragment;

    EditText idInput, lastnameInput, passwordInput;
    RadioGroup radioGroup;
    RadioButton radioAdmin, radioUser;
    String roleInput;

    TextView tvIsConnected;

    User user;
    int id = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register, container, false);

        // get reference to the views
        tvIsConnected = (TextView) rootView.findViewById(R.id.tvIsConnected);
        idInput = (EditText) rootView.findViewById(R.id.et_account);
        lastnameInput = (EditText) rootView.findViewById(R.id.et_lastname);
        passwordInput = (EditText) rootView.findViewById(R.id.et_password);
        createButton = (Button) rootView.findViewById(R.id.btn_createuser);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioAdmin = (RadioButton) rootView.findViewById(R.id.radio_admin);
        radioUser = (RadioButton) rootView.findViewById(R.id.radio_user);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_admin:
                        roleInput = "0";
                        break;
                    case R.id.radio_user:
                        roleInput = "1";
                }
            }
        });

//        if(isConnected()){
//            tvIsConnected.setBackgroundColor(0xFF00CC00);
//            tvIsConnected.setText("You are conncted");
//        }
//        else{
//            tvIsConnected.setText("You are NOT conncted");
//        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate())
                    Toast.makeText(getActivity().getBaseContext(), "Enter some data!", Toast.LENGTH_SHORT).show();
                // call AsynTask to perform network operation on separate thread
                new AsyncCreateUser().execute("http://secretary.azurewebsites.net/api/Users");
            }
        });

        return rootView;
    }

    public static String POST(String url, User user){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("FirstName", user.getFirstName());
            jsonObject.accumulate("LastName", user.getLastName());
            jsonObject.accumulate("Password", user.getPassword());
            jsonObject.accumulate("Role", user.getRole());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert User object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(user);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    protected class AsyncCreateUser extends
            AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            try {
                // TODO
                user = new User();
                user.setId(id);
                user.setFirstName(idInput.getText().toString());
                user.setLastName(lastnameInput.getText().toString());
                user.setPassword(passwordInput.getText().toString());
                user.setRole(roleInput);
                id++;
                return POST(urls[0],user);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCreateUser", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (mLoginFragment == null) {
                mLoginFragment = new LoginFragment();
            }
            Toast.makeText(getActivity().getBaseContext(), "Data Sent!", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().replace(R.id.loginContainer, mLoginFragment).commit();
        }
    }

    private boolean validate(){
        if(idInput.getText().toString().trim().equals(""))
            return false;
        else if(lastnameInput.getText().toString().trim().equals(""))
            return false;
        else if(passwordInput.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
