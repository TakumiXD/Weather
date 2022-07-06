package com.example.weatherapp;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.weatherapp.weatherdata.ForecastWeatherData;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ForecastWeatherDataTest {
    private static final String INTENT_ENABLE_GPS = "ENABLE_GPS";
    private static final String INTENT_USE_DATABASE = "USE_DATABASE";
    private static final String DATE_AND_TIME = "Sunday 12 PM";
    private static final double TEMPERATURE = 100;
    private static final String WEATHER = "Weather";
    private static final String WEATHER_ICON = "01d";

    /**
     * For some strange reason, notifyDataSetChanged in ForecastListAdapter does not call
     * onCreateViewHolder, onBindViewHolder(), and other functions during unit testing (but calls
     * them when using the app on an emulator). This makes testing using TextView.getText()
     * impossible. Therefore, instead, this function tests whether the adapter was able to set the
     * List of forecast weathers through setForecastWeatherDataList(). If the adapter was able to
     * set forecastWeatherDataList, it means that after the setter calls notifyDataSetChanged()
     * the ViewHolders should be created.
     */
    @Test
    public void testSetForecastWeatherDataDisplay() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(INTENT_ENABLE_GPS, false);
        intent.putExtra(INTENT_USE_DATABASE, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ForecastWeatherData expectedForecastWeatherData = new ForecastWeatherData(DATE_AND_TIME,
                    TEMPERATURE, WEATHER, WEATHER_ICON);
            List<ForecastWeatherData> forecastWeatherDataList = new ArrayList<>();
            forecastWeatherDataList.add(expectedForecastWeatherData);
            activity.setForecastWeatherDataDisplay(forecastWeatherDataList);
            assertEquals(expectedForecastWeatherData, activity.getForecastListAdapter().getForecastWeatherDataList().get(0));
            assertEquals(1, activity.getForecastListAdapter().getItemCount());
        });
    }
}
