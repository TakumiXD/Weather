package com.example.weatherapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class searchBehaviorTest {
    private static final String BASIC_CITY = "London";
    private static final String LONG_CITY = "Bonadelle Ranchos-Madera Ranchos";
    private static final String NEW_LINE = "\n";

    @Test
    public void basicSearch() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.etSearchBar.requestFocus();
            activity.etSearchBar.setText(BASIC_CITY);
            activity.etSearchBar.clearFocus();
            assertEquals(BASIC_CITY, activity.getEtSearchBar().getText().toString());
            activity.ibSearchButton.performClick();
            assertEquals(BASIC_CITY, activity.getPresenter().getCurrentSearchedCityName());
        });
    }

    @Test
    public void longNameSearch() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.etSearchBar.requestFocus();
            activity.etSearchBar.setText(LONG_CITY);
            activity.etSearchBar.clearFocus();
            assertFalse(activity.getEtSearchBar().getText().toString().contains(NEW_LINE));
            assertEquals(LONG_CITY, activity.getEtSearchBar().getText().toString());
            activity.ibSearchButton.performClick();
            assertFalse(activity.getPresenter().getCurrentSearchedCityName().contains(NEW_LINE));
            assertEquals(LONG_CITY, activity.getPresenter().getCurrentSearchedCityName());
        });
    }
}
