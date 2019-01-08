package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {

    private static JokeApi jokeApiService = null;
    private OnJokeEventListener mOnJokeEventListener;

    EndpointsAsyncTask(OnJokeEventListener listener) {
        mOnJokeEventListener = listener;
    }

    public static void getNewInstance(OnJokeEventListener listener) {
        new EndpointsAsyncTask(listener).execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnJokeEventListener.onGetJokeStarted();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (jokeApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setApplicationName("Build It Bigger")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            jokeApiService = builder.build();
        }
        try {
            return jokeApiService.getJokeService().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mOnJokeEventListener.onJokeReceived(result);
    }

    interface OnJokeEventListener {
        void onGetJokeStarted();

        void onJokeReceived(String result);
    }
}
