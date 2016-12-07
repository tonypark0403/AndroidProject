package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.seneca.map524.smartsecretary.MainActivity;
import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.Login;
import ca.seneca.map524.smartsecretary.util.LoginHttpClient;
import ca.seneca.map524.smartsecretary.util.LoginJsonParser;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Tony on 11/14/2016.
 */

public class LoginFragment extends Fragment {

    private final static String TAG = "LoginFragment";
    private final static String LOGIN_URL = "http://secretary.azurewebsites.net/api/Users";
    public static List<Login> mLoginList;
    View mRootView;
    RegisterFragment mRegisterFragment;

    SharedPreferences pref;
    SharedPreferences.Editor editor;// = pref.edit();

    Button loginButton, regiButton;
    EditText idInput, passwordInput;
    CheckBox autoLogin;
    boolean loginChecked = false;
    boolean validation = false;
    public static String id;
    String password;
    final String ADMIN = "0";

    //Facebook
    private CallbackManager callbackManager;
    private List<String> permissionNeeds = Arrays.asList("email");
    private Button btnLoginFaceBook;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new LoginTask().execute(LOGIN_URL);

        FacebookSdk.sdkInitialize(inflater.getContext().getApplicationContext());

        mRootView = inflater.inflate(R.layout.fragment_login, container, false);
        pref = mRootView.getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();

        regiButton = (Button)mRootView.findViewById(R.id.register);
        regiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRegisterFragment == null) {
                    mRegisterFragment = new RegisterFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.loginContainer, mRegisterFragment).commit();
            }
        });

        idInput = (EditText) mRootView.findViewById(R.id.et_username);
        passwordInput = (EditText) mRootView.findViewById(R.id.et_password);
        autoLogin = (CheckBox) mRootView.findViewById(R.id.checkBox);
        loginButton = (Button) mRootView.findViewById(R.id.btn_Login);

        if (pref != null && pref.getBoolean("autoLogin", true)) {
            autoLogin.setChecked(true);
            idInput.setText(pref.getString("id", ""));
            passwordInput.setText(pref.getString("pw", ""));
        }

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    loginChecked = true;
                } else {
                    // if unChecked, removeAll
                    loginChecked = false;
                    autoLogin.setChecked(false);
                    editor.clear();
                    editor.commit();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validation = false;
                if(autoLogin.isChecked()){
                    //Toast.makeText(mRootView.getContext(), "Auto Login", Toast.LENGTH_SHORT).show();
                    editor.putString("id", idInput.getText().toString());
                    editor.putString("pw", passwordInput.getText().toString());
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                }

                if(mLoginList.size() > 0) {
                    id = idInput.getText().toString();
                    password = passwordInput.getText().toString();
                    //                if(loginChecked) {
                    //                    if (pref.getString("id", "").equals(id) && pref.getString("pw", "").equals(password)) {
                    //                        Toast.makeText(mRootView.getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    //                        startActivity(new Intent(mRootView.getContext(), MainActivity.class));
                    //                    }
                    //                }else {
                    validation = loginValidation(id, password);
                    if(!validation){
                        Toast.makeText(mRootView.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(mRootView.getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        if(loginChecked){
                            editor.putString("id", idInput.getText().toString());
                            editor.putString("pw", passwordInput.getText().toString());
                            editor.putBoolean("autoLogin", true);
                            editor.commit();
                        }
                        //Toast.makeText(mRootView.getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mRootView.getContext(), MainActivity.class));
                    }
                    //                }
                } else {
                    Toast.makeText(mRootView.getContext(), "XML data failed", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(mRootView.getContext(), LoginActivity.class));
                }
            }
        });

        // set Facebook login callback
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Login Success");

                btnLoginFaceBook.setBackground(getResources().getDrawable(R.drawable.logout_with_facebook));

                if (AccessToken.getCurrentAccessToken() != null) {
                    Log.d(TAG, "user id: " + AccessToken.getCurrentAccessToken().getUserId());
                }

                if (Profile.getCurrentProfile() != null) {
                    Log.d(TAG, "user id: " + Profile.getCurrentProfile().getId());
                }

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                // login with Facebook
                startActivity(new Intent(mRootView.getContext(), MainActivity.class));
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Login Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "Login Error");
            }
        });


        // set Facebook login button
        btnLoginFaceBook = (Button)mRootView.findViewById(R.id.btnLoginFaceBook);
        btnLoginFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                    btnLoginFaceBook.setBackground(getResources().getDrawable(R.drawable.login_with_facebook));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, permissionNeeds);
                }
            }
        });

        // auto login with Facebook login
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(mRootView.getContext(), MainActivity.class));
        }

        // set Facebook login button background
        setFacebookLoginButton();


        return mRootView;
    }

    /**
     * set Facebook login button background image
     */
    private void setFacebookLoginButton() {
        if (AccessToken.getCurrentAccessToken() != null) {
            // status: login
            btnLoginFaceBook.setBackground(getResources().getDrawable(R.drawable.logout_with_facebook));
        } else {
            // status: logout
            btnLoginFaceBook.setBackground(getResources().getDrawable(R.drawable.login_with_facebook));
        }
    }

    private boolean loginValidation(String id, String password) {
        for (int i = 0; i < mLoginList.size(); i++) {
            if (id.equals(mLoginList.get(i).getId()) && password.equals(mLoginList.get(i).getPassword())) {
                if (mLoginList.get(i).getRole().equals(ADMIN)) {
                    //Toast.makeText(mRootView.getContext(), "Admin Log in successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(mRootView.getContext(), "User Log in successfully", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }

        Toast.makeText(mRootView.getContext(), "Login failed", Toast.LENGTH_SHORT).show();
        return false;
    }

    public class LoginTask extends AsyncTask<String, Void, List<Login>> {
        @Override
        protected List<Login> doInBackground(String... strings) {
            Log.d(TAG, "[doInBackground]");

            String data = new LoginHttpClient().getLoginData(LOGIN_URL);
            mLoginList = new ArrayList<>();

            try {
                mLoginList = LoginJsonParser.getLogin(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mLoginList;
        }

        @Override
        protected void onPostExecute(List<Login> login) {
            super.onPostExecute(login);
            Log.d(TAG, "[onPostExecute]");
        }
    }

        //Facebook
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

