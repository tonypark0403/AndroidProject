package ca.seneca.map524.smartsecretary;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Wonho on 10/25/2016.
 */
public class SpeechRecognizerManager {
    protected AudioManager mAudioManager;
    protected SpeechRecognizer mSpeechRecognizer;
    protected Intent mSpeechRecognizerIntent;

    protected boolean mIsListening;
    private boolean mIsStreamSolo;

    private boolean mMute = true;

    private final static String TAG = "SpeechRecognizerManager";

    private onResultsReady mListener;


    public SpeechRecognizerManager(Context context, onResultsReady listener) {
        try {
            mListener = listener;
        } catch (ClassCastException e) {
            Log.e(TAG, e.toString());
        }
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                context.getPackageName());
        startListening();
    }

    private void listenAgain() {
        if (mIsListening) {
            mIsListening = false;
            mSpeechRecognizer.cancel();
            startListening();
        }
    }

    private void startListening() {
        if (!mIsListening) {
            mIsListening = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // turn off beep sound
                if (!mIsStreamSolo && mMute) {
                    mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_RING, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                    mIsStreamSolo = true;
                }
            }
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        }
    }

    public void destroy() {
        mIsListening = false;
        if (!mIsStreamSolo) {
            mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_RING, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
            mIsStreamSolo = true;
        }
        Log.d(TAG, "onDestroy");
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.stopListening();
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
            mSpeechRecognizer = null;
        }
    }

    protected class SpeechRecognitionListener implements RecognitionListener {
        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void  onError(int i) {
            /*
            public static final int ERROR_AUDIO = 3;
            public static final int ERROR_CLIENT = 5;
            public static final int ERROR_INSUFFICIENT_PERMISSIONS = 9;
            public static final int ERROR_NETWORK = 2;
            public static final int ERROR_NETWORK_TIMEOUT = 1;
            public static final int ERROR_NO_MATCH = 7;
            public static final int ERROR_RECOGNIZER_BUSY = 8;
            public static final int ERROR_SERVER = 4;
            public static final int ERROR_SPEECH_TIMEOUT = 6;
            */

            if (i == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                if (mListener != null) {
                    ArrayList<String> errorList = new ArrayList<String>(1);
                    errorList.add("ERROR RECOGNIZER BUSY");
                    if (mListener != null)
                        mListener.onResults(errorList);
                }
                return;
            }

            if (i == SpeechRecognizer.ERROR_NO_MATCH) {
                if (mListener != null)
                    mListener.onResults(null);
            }

            if (i == SpeechRecognizer.ERROR_NETWORK) {
                ArrayList<String> errorList = new ArrayList<String>(1);
                errorList.add("STOPPED LISTENING");
                if (mListener != null)
                    mListener.onResults(errorList);
            }
            Log.d(TAG, "error = " + i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listenAgain();
                }
            }, 100);
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onResults(Bundle bundle) {
            if (bundle != null && mListener != null)
                mListener.onResults(bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
            listenAgain();
        }
    }

    public boolean ismIsListening() {
        return mIsListening;
    }

    public interface onResultsReady {
        public void onResults(ArrayList<String> results);
    }
}
