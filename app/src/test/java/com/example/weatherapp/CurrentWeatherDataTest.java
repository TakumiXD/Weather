package com.example.weatherapp;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import com.example.weatherapp.weatherdata.CurrentWeatherData;

@RunWith(AndroidJUnit4.class)
public class CurrentWeatherDataTest {

    @Test
    public void testSetCurrentWeatherDataDisplay() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            CurrentWeatherData mockCurrentWeatherData = new CurrentWeatherData
                    ("RandomCity", 100, "Weather", 105, 95, 10, 20, "01d");
            activity.setCurrentWeatherDataDisplay(mockCurrentWeatherData);
            assertEquals(activity.getTvCityName().getText(), "RandomCity");
            assertEquals(activity.getTvTemperatureNum().getText(), "100.0\u00B0");
            assertEquals(activity.getTvWeather().getText(), "Weather");
            assertEquals(activity.getTvMaxTempNum().getText(), "105.0\u00B0");
            assertEquals(activity.getTvMinTempNum().getText(), "95.0\u00B0");
            assertEquals(activity.getTvHumidityNum().getText(), "10.0%");
            assertEquals(activity.getTvWindSpeedNum().getText(), "20.0mph");
        });
    }
}
