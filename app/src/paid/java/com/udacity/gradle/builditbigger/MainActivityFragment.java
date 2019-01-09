package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;
import com.zizohanto.androidjokes.MainActivityAndroidModule;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.OnJokeEventListener {
    private FragmentMainBinding mFragmentMainBinding;
    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View root = mFragmentMainBinding.getRoot();

        mContext = getContext();

        mFragmentMainBinding.btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });
        return root;
    }

    private void tellJoke() {
        EndpointsAsyncTask.getNewInstance(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onGetJokeStarted() {
        // Show the progress bar
        mFragmentMainBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onJokeReceived(String result) {
        // Hide the progress bar
        mFragmentMainBinding.progressBar.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(result)) {
            final Intent intent = MainActivityAndroidModule.newStartIntent(mContext, result);
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, getString(R.string.error_getting_jokes), Toast.LENGTH_SHORT).show();
        }
    }
}
