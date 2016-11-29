package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import ca.seneca.map524.smartsecretary.R;

/**
 * Created by Wonho on 11/5/2016.
 */
public class PhotoframeFragment extends Fragment implements ViewSwitcher.ViewFactory {

    private static final int IMAGE_COUNT = 6;

    // ImageSwitcher
    private ImageSwitcher mImageSwitcher;
    private Handler mHandler;
    private int mIndex;
    private boolean mIsActivated;

    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_photoframe, container, false);

        // set ImageSwitcher
        mImageSwitcher = (ImageSwitcher)mRootView.findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(this);
        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), android.R.anim.fade_in));
        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), android.R.anim.fade_out));
        mImageSwitcher.setImageResource(R.drawable.photo1);

        // timer with Handler
        mIsActivated = true;
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (mIsActivated == true) {
                    // set image for ImageSwitcher
                    setImage();

                    // run every 3 second
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 3000);


        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        // stop timer
        mIsActivated = false;
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(mRootView.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return imageView;
    }

    private void setImage() {
        switch (++mIndex % IMAGE_COUNT) {
            case 0:
                mImageSwitcher.setImageResource(R.drawable.photo1);
                break;
            case 1:
                mImageSwitcher.setImageResource(R.drawable.photo2);
                break;
            case 2:
                mImageSwitcher.setImageResource(R.drawable.photo3);
                break;
            case 3:
                mImageSwitcher.setImageResource(R.drawable.photo4);
                break;
            case 4:
                mImageSwitcher.setImageResource(R.drawable.photo5);
                break;
            case 5:
                mImageSwitcher.setImageResource(R.drawable.photo6);
                break;
        }
    }
}
