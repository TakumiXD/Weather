package com.example.weatherapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchBehaviorTest {
    private ActivityScenario<MainActivity> scenario;

    private static final String BASIC_CITY = "London";
    private static final String LONG_CITY = "Bonadelle Ranchos-Madera Ranchos";
    private static final String NEW_LINE = "\n";

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void basicSearch() {
        scenario.onActivity(activity -> {
            activity.getEtSearchBar().requestFocus();
            activity.getEtSearchBar().setText(BASIC_CITY);
            activity.getEtSearchBar().clearFocus();
            assertEquals(BASIC_CITY, activity.getEtSearchBar().getText().toString());
            activity.getIbSearchButton().performClick();
            assertEquals(BASIC_CITY, activity.getPresenter().getCurrentSearchedCityName());
        });
    }

    @Test
    public void longNameSearch() {
        scenario.onActivity(activity -> {
            activity.getEtSearchBar().requestFocus();
            activity.getEtSearchBar().setText(LONG_CITY);
            activity.getEtSearchBar().clearFocus();
            assertFalse(activity.getEtSearchBar().getText().toString().contains(NEW_LINE));
            assertEquals(LONG_CITY, activity.getEtSearchBar().getText().toString());
            activity.getIbSearchButton().performClick();
            assertFalse(activity.getPresenter().getCurrentSearchedCityName().contains(NEW_LINE));
            assertEquals(LONG_CITY, activity.getPresenter().getCurrentSearchedCityName());
        });
    }
}
