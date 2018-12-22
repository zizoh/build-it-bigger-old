package com.zizohanto.androidjokes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.common.base.Strings;

public class MainActivityAndroidModule extends AppCompatActivity {

    public final static String KEY_EXTRA_MESSAGE = "com.zizohanto.androidjokes.MESSAGE";

    /**
     * Creates an {@link Intent} for {@link MainActivityAndroidModule} with the message to be displayed.
     *
     * @param context the {@link Context} where the {@link Intent} will be used
     * @param message a {@link String} with text to be displayed
     * @return an {@link Intent} used to start {@link MainActivityAndroidModule}
     */
    static public Intent newStartIntent(Context context, String message) {
        Intent newIntent = new Intent(context, MainActivityAndroidModule.class);
        newIntent.putExtra(KEY_EXTRA_MESSAGE, message);
        return newIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_android_module);

        // Get the message from the Intent.
        Intent intent = getIntent();
        String message = Strings.nullToEmpty(intent.getStringExtra(KEY_EXTRA_MESSAGE));

        // Show message.
        ((TextView) findViewById(R.id.tv_jokes)).setText(message);
    }

}
