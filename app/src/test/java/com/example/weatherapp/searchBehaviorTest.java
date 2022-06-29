package com.example.weatherapp;

import static org.junit.Assert.assertEquals;

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
    @Test
    public void testSearch() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.etSearchBar.requestFocus();
            activity.etSearchBar.setText("London");
            activity.etSearchBar.clearFocus();
            activity.ibSearchButton.performClick();
            assertEquals("London", activity.getEtSearchBar().getText().toString());
            assertEquals("London", activity.getPresenter().getCurrentSearchedCityName());
        });
    }
}
