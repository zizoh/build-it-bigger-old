package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;
import com.zizohanto.androidjokes.MainActivityAndroidModule;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.OnJokeEventListener {
    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private Context mContext;
    private InterstitialAd mInterstitialAd;
    private FragmentMainBinding mFragmentMainBinding;
    private String mJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View root = mFragmentMainBinding.getRoot();

        mContext = getContext();
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mFragmentMainBinding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdClosed() {
                // Show the progress bar
                mFragmentMainBinding.progressBar.setVisibility(View.VISIBLE);

                // If joke has been received, start activity to display the joke
                if (!TextUtils.isEmpty(mJoke)) {
                    startActivityToDisplayJoke();
                }
            }

        });

        mFragmentMainBinding.btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });
        return root;
    }

    private void tellJoke() {
        mFragmentMainBinding.progressBar.setVisibility(View.VISIBLE);
        EndpointsAsyncTask.getNewInstance(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onGetJokeStarted() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d(TAG, getString(R.string.interstitial_load_error));
        }
    }

    @Override
    public void onJokeReceived(String result) {
        // Hide the progress bar
        mFragmentMainBinding.progressBar.setVisibility(View.GONE);
        mJoke = result;
        if (!TextUtils.isEmpty(result)) {
            if (!mInterstitialAd.isLoaded()) {
                startActivityToDisplayJoke();
            }
        } else {
            Toast.makeText(mContext, getString(R.string.error_getting_jokes), Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivityToDisplayJoke() {
        mFragmentMainBinding.progressBar.setVisibility(View.GONE);
        final Intent intent = MainActivityAndroidModule.newStartIntent(mContext, mJoke);
        mContext.startActivity(intent);
    }
}
