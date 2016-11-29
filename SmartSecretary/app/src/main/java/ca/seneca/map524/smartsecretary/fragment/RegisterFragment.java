package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ca.seneca.map524.smartsecretary.R;
import ca.seneca.map524.smartsecretary.model.LoginCreate;

/**
 * Created by Tony on 11/14/2016.
 */

public class RegisterFragment extends Fragment {
    private final static String TAG = "RegisterFragment";

    Button createButton;
    LoginFragment mLoginFragment;

    EditText idInput, passwordInput;

    String id, password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        idInput = (EditText) rootView.findViewById(R.id.et_cu_username);
        passwordInput = (EditText) rootView.findViewById(R.id.et_cu_password);

        createButton = (Button) rootView.findViewById(R.id.btn_createuser);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idInput.getText().toString();
                password = passwordInput.getText().toString();

                LoginCreate loginCreate = new LoginCreate(id, password);
                new AsyncCreateUser().execute(loginCreate);
            }
        });
        return rootView;
    }

    protected class AsyncCreateUser extends
            AsyncTask<LoginCreate, Void, Void> {

        @Override
        protected Void doInBackground(LoginCreate... params) {

            try {
                // TODO

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCreateUser", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (mLoginFragment == null) {
                mLoginFragment = new LoginFragment();
            }
            getFragmentManager().beginTransaction().replace(R.id.loginContainer, mLoginFragment).commit();
        }
    }
}
