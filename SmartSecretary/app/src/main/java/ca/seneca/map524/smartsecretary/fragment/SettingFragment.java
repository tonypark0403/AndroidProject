package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ca.seneca.map524.smartsecretary.R;

import static ca.seneca.map524.smartsecretary.MainActivity.views;
import static ca.seneca.map524.smartsecretary.fragment.LoginFragment.id;

/**
 * Created by Tony on 12/1/2016.
 */

public class SettingFragment extends Fragment {

    private final static String TAG = "SettingFragment";
    View rootView;
    SettingFragment mSettingFragment;

    EditText accountInfo;
    RadioGroup radioGroup;
    RadioButton radioOn, radioOff;

    boolean check = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioOn = (RadioButton) rootView.findViewById(R.id.radio_on);
        radioOff = (RadioButton) rootView.findViewById(R.id.radio_off);
        accountInfo = (EditText) rootView.findViewById(R.id.et_account);

        accountInfo.setText(id);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_on:
                        check = true;
                        break;
                    case R.id.radio_off:
                        check = false;
                }
            }
        });

        if(check){
            for (int i = 0; i < views.size(); i++) {
                //views.get(i).getLayoutParams().height = 1;
                views.get(i).setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < views.size(); i++) {
                //views.get(i).getLayoutParams().height = 1;
                views.get(i).setVisibility(View.INVISIBLE);
            }
        }
        return rootView;
    }
}