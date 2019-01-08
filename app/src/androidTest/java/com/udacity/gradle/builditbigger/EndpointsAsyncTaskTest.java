package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest implements EndpointsAsyncTask.OnJokeEventListener {

    @Test
    public void AsyncTest() {
        try {
            EndpointsAsyncTask.getNewInstance(this);
        } catch (Exception e) {
            fail("Exception while getting joke: " + e.getMessage());
        }

    }

    @Override
    public void onGetJokeStarted() {

    }

    @Override
    public void onJokeReceived(String result) {
        assertFalse("joke is empty", result.isEmpty());
    }
}