package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LocationTrackingTest {
    private static final String INTENT_ENABLE_GPS = "ENABLE_GPS";
    private static final String INTENT_USE_DATABASE = "USE_DATABASE";
    private static final Pair<Double, Double> SAN_DIEGO_COORDINATES = Pair.create(32.7157, -117.1611);
    private static final Pair<Double, Double> OSAKA_COORDINATES = Pair.create(34.6937, 135.5023);

    @Test
    public void testMockLocation() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(INTENT_ENABLE_GPS, false);
        intent.putExtra(INTENT_USE_DATABASE, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
           activity.mockCoordinates(SAN_DIEGO_COORDINATES);
           assertEquals(SAN_DIEGO_COORDINATES, activity.getPresenter().getModel().getCoordinates().getValue());
           activity.mockCoordinates(OSAKA_COORDINATES);
            assertEquals(OSAKA_COORDINATES, activity.getPresenter().getModel().getCoordinates().getValue());
        });
    }

}
