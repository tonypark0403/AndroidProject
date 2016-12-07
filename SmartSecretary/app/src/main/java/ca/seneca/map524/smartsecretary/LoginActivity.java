package ca.seneca.map524.smartsecretary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.util.Arrays;
import java.util.List;

import ca.seneca.map524.smartsecretary.fragment.LoginFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginActivity extends Activity {

    LoginFragment mloginFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set landscape screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (mloginFragment == null) {
            mloginFragment = new LoginFragment();
            //mFirstFragment.setArguments(getIntent().getExtras());
        }
        getFragmentManager().beginTransaction().replace(R.id.loginContainer, mloginFragment).commit();
    }
}
