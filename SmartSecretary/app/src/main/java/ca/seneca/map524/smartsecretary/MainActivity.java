package ca.seneca.map524.smartsecretary;

import android.Manifest;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.ArrayList;
import java.util.List;

import ca.seneca.map524.smartsecretary.fragment.CancellationFragment;
import ca.seneca.map524.smartsecretary.fragment.GoogleMapFragment;
import ca.seneca.map524.smartsecretary.fragment.HomeFragment;
import ca.seneca.map524.smartsecretary.fragment.NewsHeadlineFragment;
import ca.seneca.map524.smartsecretary.fragment.PhotoframeFragment;
import ca.seneca.map524.smartsecretary.fragment.SettingFragment;
import ca.seneca.map524.smartsecretary.fragment.TTCFragment;
import ca.seneca.map524.smartsecretary.fragment.TimetableFragment;
import ca.seneca.map524.smartsecretary.fragment.WeatherFragment;
import ca.seneca.map524.smartsecretary.philipshue.AccessPointListAdapter;
import ca.seneca.map524.smartsecretary.philipshue.HueSharedPreferences;
import ca.seneca.map524.smartsecretary.philipshue.PHHomeActivity;
import ca.seneca.map524.smartsecretary.philipshue.PHPushlinkActivity;
import ca.seneca.map524.smartsecretary.philipshue.PHWizardAlertDialog;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    // hide for fullscreen
    private View mContentView;

    // fragments
    HomeFragment mHomeFragment;
    WeatherFragment mWeatherFragment;
    TTCFragment mTTCFragment;
    NewsHeadlineFragment mNewsHeadlineFragment;
    TimetableFragment mTimetableFragment;
    CancellationFragment mCancellationFragment;
    PhotoframeFragment mPhotoframeFragment;
    GoogleMapFragment mGoogleMapFragment;
    SettingFragment mSettingFragment;

    //setting button
    public static ArrayList<View> views;

    // speech
    private SpeechRecognizerManager mSpeechManager;
    // to test speech
    private TextView mTextSpeech;

    // Philips Hue
    private PHHueSDK phHueSDK;
    private AccessPointListAdapter mHueAdapter;
    private HueSharedPreferences mHuePrefs;
    private boolean lastSearchWasIPScan = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // request audio permissions
        checkPermissions();

        // hide to make fullscreen
        hide();

        // set landscape screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // set buttons
        setButtonListener();

        // connect Philips Hue
        // this must need Philips Hue device
        //connectHue();

        // start listening
        startListening();

        // add home fragment
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        addFragment(mHomeFragment);
    }

    private void checkPermissions() {

        // permissions to use
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,               // speech
                Manifest.permission.ACCESS_FINE_LOCATION,       // map
                Manifest.permission.ACCESS_COARSE_LOCATION,     // geocoder for map
                Manifest.permission.WRITE_EXTERNAL_STORAGE      // capture screen for map
        };

        // required permission list to request
        ArrayList<String> requiredPermissionList = new ArrayList<String>();

        // check permissions
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    // notify the required permission
                    Toast.makeText(this, permission + " is required", Toast.LENGTH_LONG).show();
                }

                // add the required permission
                requiredPermissionList.add(permission);
            }
        }

        // request permissions not granted
        if (requiredPermissionList.size() > 0) {
            // list to array
            String[] requiredPermissions = new String[requiredPermissionList.size()];
            requiredPermissionList.toArray(requiredPermissions);

            // request the required permissions
            ActivityCompat.requestPermissions(this, requiredPermissions, 1);
        }
    }

    /**
     * set button listener for test
     */
    private void setButtonListener() {
        // home button
        Button buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment home
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                replaceFragment(mHomeFragment);

                // restart speech listner
                startListening();
            }
        });

        // weather button
        Button buttonWeather = (Button) findViewById(R.id.buttonWeather);
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment weather
                if (mWeatherFragment == null) {
                    mWeatherFragment = new WeatherFragment();
                }
                replaceFragment(mWeatherFragment);

                // restart speech listner
                startListening();
            }
        });

        // TTC button
        Button buttonTTC = (Button) findViewById(R.id.buttonTTC);
        buttonTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment TTC
                mTTCFragment = new TTCFragment();
                mTTCFragment.setBusStop("york university");
                replaceFragment(mTTCFragment);

                // restart speech listner
                startListening();
            }
        });

        // News button
        Button buttonNews = (Button) findViewById(R.id.buttonNews);
        buttonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment Timetable
                if (mNewsHeadlineFragment == null) {
                    mNewsHeadlineFragment = new NewsHeadlineFragment();
                }
                replaceFragment(mNewsHeadlineFragment);

                // restart speech listner
                startListening();
            }
        });

        // Timetable button
        Button buttonTimetable = (Button) findViewById(R.id.buttonTimetable);
        buttonTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment Timetable
                if (mTimetableFragment == null) {
                    mTimetableFragment = new TimetableFragment();
                }

                replaceFragment(mTimetableFragment);

                // restart speech listner
                startListening();
            }
        });

        // Class cancellation button
        Button buttonCancellation = (Button) findViewById(R.id.buttonCancelation);
        buttonCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment Timetable
                if (mCancellationFragment == null) {
                    mCancellationFragment = new CancellationFragment();
                }
                replaceFragment(mCancellationFragment);

                // restart speech listner
                startListening();
            }
        });

        // Digital photo frame button
        Button buttonPhotoframe = (Button) findViewById(R.id.buttonPhotoframe);
        buttonPhotoframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // replace fragment Timetable
                if (mPhotoframeFragment == null) {
                    mPhotoframeFragment = new PhotoframeFragment();
                }
                replaceFragment(mPhotoframeFragment);

                // restart speech listner
                startListening();
            }
        });

        // map button
        Button buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleMapFragment = new GoogleMapFragment();
                /*Bundle bundle = new Bundle();
                bundle.putString("location", "toronto");
                mGoogleMapFragment.setArguments(bundle);*/
                mGoogleMapFragment.setLocation("toronto");
                replaceFragment(mGoogleMapFragment);

                // restart speech listner
                startListening();
            }
        });


        // Hue fragment (will be moved to settings Activity)
        Button buttonHue = (Button) findViewById(R.id.buttonHue);
        buttonHue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hue clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), PHHomeActivity.class);
                /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    intent.addFlags(0x8000); // equal to Intent.FLAG_ACTIVITY_CLEAR_TASK which is only available from API level 11*/
                startActivityForResult(intent, 0);
            }
        });

        Button buttonOn = (Button) findViewById(R.id.buttonOn);
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOffLights(true);

                // restart speech listner
                startListening();
            }
        });

        Button buttonOff = (Button) findViewById(R.id.buttonOff);
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOffLights(false);

                // restart speech listner
                startListening();
            }
        });

        Button buttonWhite = (Button) findViewById(R.id.buttonWhite);
        buttonWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorLights("White");

                // restart speech listner
                startListening();
            }
        });

        Button buttonPink = (Button) findViewById(R.id.buttonPink);
        buttonPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorLights("Pink");

                // restart speech listner
                startListening();
            }
        });

        Button buttonRed = (Button) findViewById(R.id.buttonRed);
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorLights("Red");

                // restart speech listner
                startListening();
            }
        });

        Button buttonGreen = (Button) findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorLights("Green");

                // restart speech listner
                startListening();
            }
        });

        Button buttonBlue = (Button) findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorLights("Blue");

                // restart speech listner
                startListening();
            }
        });

        Button buttonYellow = (Button) findViewById(R.id.buttonYellow);
        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColorLights("Yellow");

                // restart speech listner
                startListening();
            }
        });

        mTextSpeech = (TextView) findViewById(R.id.textSpeech);

        // test code
        views = new ArrayList<View>();
        /*views.add(buttonHome);
        views.add(buttonWeather);
        views.add(buttonMap);
        views.add(buttonTTC);
        views.add(buttonNews);
        views.add(buttonTimetable);
        views.add(buttonCancellation);
        views.add(buttonPhotoframe);
        views.add(mTextSpeech);*/
        views.add(buttonOn);
        views.add(buttonOff);
        views.add(buttonWhite);
        views.add(buttonPink);
        views.add(buttonRed);
        views.add(buttonGreen);
        views.add(buttonBlue);
        views.add(buttonYellow);
        views.add(buttonHue);
        for (int i = 0; i < views.size(); i++) {
            views.get(i).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            hide();
        }
    }

    /**
     * add a fragment
     *
     * @param fragment
     */
    private void addFragment(Fragment fragment) {
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    /**
     * replace a fragment
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        try {
            fragment.setArguments(getIntent().getExtras());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * hide to make fullscreen
     */
    private void hide() {
        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // hide system UI
        mContentView = findViewById(R.id.container);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * set speech listener for voice control
     */
    private void SetSpeechListener() {
        mSpeechManager = new SpeechRecognizerManager(this, new SpeechRecognizerManager.onResultsReady() {
            @Override
            public void onResults(ArrayList<String> results) {

                if (results != null && results.size() > 0) {
                    if (results.size() == 1) {
                        mSpeechManager.destroy();
                        mSpeechManager = null;
                        //mTextView.setText(results.get(0));
                        Log.d(TAG, results.get(0));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        if (results.size() > 5) {
                            results = (ArrayList<String>) results.subList(0, 5);
                        }
                        for (String result : results) {
                            sb.append(result).append("\n");
                        }
                        //mTextView.setText(sb.toString());
                        Log.d(TAG, sb.toString());

                        checkCommand(results);
                    }
                } else {
                    //mTextView.setText(getString(R.string.no_results_found));
                    //Log.d(TAG, getString(R.string.no_results_found));
                }
            }
        });
    }

    /**
     * check the command what we say
     *
     * @param results
     */
    private void checkCommand(ArrayList<String> results) {
        // test code
        mTextSpeech.setText(results.get(0));

        for (String result : results) {
            String command = result.toLowerCase().trim();

            if (command.contains("show me the map") || command.contains("map")) {
                // map command
                runMapCommand(command);
                break;
            } else if (command.contains("show me the bus") || command.contains("bus")) {
                // TTC command
                runTTCCommand(command);
                break;
            } else {
                // other commands
                if (runCommand(command) == true) {
                    break;
                }
            }
        }
    }

    /**
     * run the command for Google map
     *
     * @param command
     */
    private void runMapCommand(String command) {
        mGoogleMapFragment = new GoogleMapFragment();

        // show me the map Toronto
        int index;
        if (command.contains("show me the map")) {
            index = command.indexOf("show me the map") + 15;
        } else {
            index = command.indexOf("map") + 3;
        }

        if (index < command.length()) {
            String location = command.substring(index);
            mGoogleMapFragment.setLocation(location.trim());
        }

        replaceFragment(mGoogleMapFragment);
    }

    /**
     * run the command for TTC
     *
     * @param command
     */
    private void runTTCCommand(String command) {
        mTTCFragment = new TTCFragment();

        // show me the but York University
        int index;
        if (command.contains("show me the bus")) {
            index = command.indexOf("show me the bus") + 15;
        } else {
            index = command.indexOf("bus") + 3;
        }

        if (index < command.length()) {
            String busStop = command.substring(index);
            mTTCFragment.setBusStop(busStop.trim());
        }

        replaceFragment(mTTCFragment);
    }

    /**
     * run the command for home, weather, Philips Hue
     *
     * @param command
     * @return
     */
    private boolean runCommand(String command) {
        switch (command) {
            // home
            case "go home":
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                replaceFragment(mHomeFragment);
                return true;

            // weather
            case "show me weather":
            case "show me the weather":
                if (mWeatherFragment == null) {
                    mWeatherFragment = new WeatherFragment();
                }
                replaceFragment(mWeatherFragment);
                return true;

            // timetable
            case "show me timetable":
            case "show me the timetable":
                if (mTimetableFragment == null) {
                    mTimetableFragment = new TimetableFragment();
                }
                replaceFragment(mTimetableFragment);
                return true;

            // cancellations
            case "cancel":
            case "cancellation":
            case "cancellations":
            case "show me cancellation":
            case "show me the cancellation":
            case "show me class cancellation":
            case "show me the class cancellation":
            case "show me cancellations":
            case "show me the cancellations":
            case "show me class cancellations":
            case "show me the class cancellations":
                if (mCancellationFragment == null) {
                    mCancellationFragment = new CancellationFragment();
                }
                replaceFragment(mCancellationFragment);
                return true;

            // photo frame
            case "show me photo":
            case "show me the photo":
            case "show me photo frame":
            case "show me the photo frame":
                if (mPhotoframeFragment == null) {
                    mPhotoframeFragment = new PhotoframeFragment();
                }
                replaceFragment(mPhotoframeFragment);
                return true;

            // news (CBC)
            case "show me news":
            case "show me the news":
                if (mNewsHeadlineFragment == null) {
                    mNewsHeadlineFragment = new NewsHeadlineFragment();
                }
                replaceFragment(mNewsHeadlineFragment);
                return true;
            case "first":
            case "show me first news article":
            case "show me the first news article":
            case "show me first article":
            case "show me the first article":
            case "show me first news":
            case "show me the first news":
                if (mNewsHeadlineFragment != null) {
                    mNewsHeadlineFragment.displayNewsArticle(0);
                }
                return true;
            case "second":
            case "show me second news article":
            case "show me the second news article":
            case "show me second article":
            case "show me the second article":
            case "show me second news":
            case "show me the second news":
                if (mNewsHeadlineFragment != null) {
                    mNewsHeadlineFragment.displayNewsArticle(1);
                }
                return true;
            case "third":
            case "show me third news article":
            case "show me the third news article":
            case "show me third article":
            case "show me the third article":
            case "show me third news":
            case "show me the third news":
                if (mNewsHeadlineFragment != null) {
                    mNewsHeadlineFragment.displayNewsArticle(2);
                }
                return true;
            case "fourth":
            case "show me fourth news article":
            case "show me the fourth news article":
            case "show me fourth article":
            case "show me the fourth article":
            case "show me fourth news":
            case "show me the fourth news":
                if (mNewsHeadlineFragment != null) {
                    mNewsHeadlineFragment.displayNewsArticle(3);
                }
                return true;

            // Philips Hue
            case "turn on":
            case "turn on light":
            case "turn on the light":
                onOffLights(true);
                return true;
            case "turn off":
            case "turn off light":
            case "turn off the light":
                onOffLights(false);
                return true;
            case "white":
            case "change light white":
            case "change the light white":
                changeColorLights("White");
                return true;
            case "pink":
            case "change light pink":
            case "change the light pink":
                changeColorLights("Pink");
                return true;
            case "red":
            case "change light red":
            case "change the light red":
                changeColorLights("Red");
                return true;
            case "green":
            case "change light green":
            case "change the light green":
                changeColorLights("Green");
                return true;
            case " blue":
            case "change light blue":
            case "change the light blue":
                changeColorLights("Blue");
                return true;
            case "yellow":
            case "change light yellow":
            case "change the light yellow":
                changeColorLights("Yellow");
                return true;
        }

        return false;
    }

    /**
     * start listening for voice control
     */
    private void startListening() {
        if (mSpeechManager == null) {
            Log.d(TAG, "[startListening] mSpeechManager == null");

            SetSpeechListener();
        } else if (!mSpeechManager.ismIsListening()) {
            Log.d(TAG, "[startListening] !mSpeechManager.ismIsListening()");

            mSpeechManager.destroy();
            mSpeechManager = null;
            SetSpeechListener();
        }
    }

    /**
     * Local SDK Listener for Philips Hue
     */
    private PHSDKListener listener = new PHSDKListener() {

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPoint) {
            Log.w(TAG, "Access Points Found. " + accessPoint.size());

            PHWizardAlertDialog.getInstance().closeProgressDialog();
            if (accessPoint != null && accessPoint.size() > 0) {
                phHueSDK.getAccessPointsFound().clear();
                phHueSDK.getAccessPointsFound().addAll(accessPoint);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHueAdapter.updateData(phHueSDK.getAccessPointsFound());
                    }
                });
            }
        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
            Log.w(TAG, "On CacheUpdated");

        }

        @Override
        public void onBridgeConnected(PHBridge b, String username) {
            phHueSDK.setSelectedBridge(b);
            phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
            phHueSDK.getLastHeartbeat().put(b.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
            mHuePrefs.setLastConnectedIPAddress(b.getResourceCache().getBridgeConfiguration().getIpAddress());
            mHuePrefs.setUsername(username);
            PHWizardAlertDialog.getInstance().closeProgressDialog();
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            Log.w(TAG, "Authentication Required.");
            phHueSDK.startPushlinkAuthentication(accessPoint);
            startActivity(new Intent(MainActivity.this, PHPushlinkActivity.class));

        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {
            if (MainActivity.this.isFinishing())
                return;

            Log.v(TAG, "onConnectionResumed" + bridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
            for (int i = 0; i < phHueSDK.getDisconnectedAccessPoint().size(); i++) {

                if (phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache().getBridgeConfiguration().getIpAddress())) {
                    phHueSDK.getDisconnectedAccessPoint().remove(i);
                }
            }
        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            Log.v(TAG, "onConnectionLost : " + accessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(accessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(accessPoint);
            }
        }

        @Override
        public void onError(int code, final String message) {
            Log.e(TAG, "on Error Called : " + code + ":" + message);

            if (code == PHHueError.NO_CONNECTION) {
                Log.w(TAG, "On No Connection");
            } else if (code == PHHueError.AUTHENTICATION_FAILED || code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            } else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(TAG, "Bridge Not Responding . . . ");
                PHWizardAlertDialog.getInstance().closeProgressDialog();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PHWizardAlertDialog.showErrorDialog(MainActivity.this, message, R.string.hue_btn_ok);
                    }
                });

            } else if (code == PHMessageType.BRIDGE_NOT_FOUND) {

                if (!lastSearchWasIPScan) {  // Perform an IP Scan (backup mechanism) if UPNP and Portal Search fails.
                    phHueSDK = PHHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);
                    lastSearchWasIPScan = true;
                } else {
                    PHWizardAlertDialog.getInstance().closeProgressDialog();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PHWizardAlertDialog.showErrorDialog(MainActivity.this, message, R.string.hue_btn_ok);
                        }
                    });
                }
            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError : parsingErrorsList) {
                Log.e(TAG, "ParsingError : " + parsingError.getMessage());
            }
        }
    };

    /**
     * connect Philips Hue
     */
    private void connectHue() {
        // Gets an instance of the Hue SDK.
        phHueSDK = PHHueSDK.create();

        // Set the Device Name (name of your app). This will be stored in your bridge whitelist entry.
        phHueSDK.setAppName(getResources().getString(R.string.app_name));
        phHueSDK.setDeviceName(android.os.Build.MODEL);

        // Register the PHSDKListener to receive callbacks from the bridge.
        phHueSDK.getNotificationManager().registerSDKListener(listener);

        // Try to automatically connect to the last known bridge.  For first time use this will be empty so a bridge search is automatically started.
        mHuePrefs = HueSharedPreferences.getInstance(getApplicationContext());
        String lastIpAddress = mHuePrefs.getLastConnectedIPAddress();
        String lastUsername = mHuePrefs.getUsername();

        // Automatically try to connect to the last connected IP Address.  For multiple bridge support a different implementation is required.
        if (lastIpAddress != null && !lastIpAddress.equals("")) {
            PHAccessPoint lastAccessPoint = new PHAccessPoint();
            lastAccessPoint.setIpAddress(lastIpAddress);
            lastAccessPoint.setUsername(lastUsername);

            if (!phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                PHWizardAlertDialog.getInstance().showProgressDialog(R.string.hue_connecting, MainActivity.this);
                phHueSDK.connect(lastAccessPoint);
            }
        }
    }

    /**
     * turn on/off Philips Hue light
     *
     * @param on
     */
    public void onOffLights(boolean on) {

        if (phHueSDK != null) {
            PHBridge bridge = phHueSDK.getSelectedBridge();
            if (bridge != null) {
                List<PHLight> allLights = bridge.getResourceCache().getAllLights();

                for (PHLight light : allLights) {
                    PHLightState lightState = new PHLightState();
                    lightState.setOn(on);
                    bridge.setLightStateForDefaultGroup(lightState);
                }
            }
        }
    }

    /**
     * change color Philips Hue light
     *
     * @param color
     */
    public void changeColorLights(String color) {

        if (phHueSDK != null) {
            PHBridge bridge = phHueSDK.getSelectedBridge();
            if (bridge != null) {
                List<PHLight> allLights = bridge.getResourceCache().getAllLights();

                PHLightState lightState = new PHLightState();
                float xy[] = null;
                switch (color) {
                    case "Pink":
                        xy = PHUtilities.calculateXYFromRGB(255, 0, 255, allLights.get(0).getModelNumber());
                        break;
                    case "Red":
                        xy = PHUtilities.calculateXYFromRGB(255, 0, 0, allLights.get(0).getModelNumber());
                        break;
                    case "Green":
                        xy = PHUtilities.calculateXYFromRGB(0, 255, 0, allLights.get(0).getModelNumber());
                        break;
                    case "Blue":
                        xy = PHUtilities.calculateXYFromRGB(0, 0, 255, allLights.get(0).getModelNumber());
                        break;
                    case "Yellow":
                        xy = PHUtilities.calculateXYFromRGB(255, 255, 0, allLights.get(0).getModelNumber());
                        break;
                    case "White":
                    default:
                        xy = PHUtilities.calculateXYFromRGB(255, 255, 255, allLights.get(0).getModelNumber());
                        break;
                }

                lightState.setX(xy[0]);
                lightState.setY(xy[1]);

                bridge.setLightStateForDefaultGroup(lightState);
            }
        }
    }

    @Override
    protected void onDestroy() {
        // disconnect Philips Hue
        try {
            PHBridge bridge = phHueSDK.getSelectedBridge();
            if (bridge != null) {

                if (phHueSDK.isHeartbeatEnabled(bridge)) {
                    phHueSDK.disableHeartbeat(bridge);
                }

                phHueSDK.disconnect(bridge);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }
}