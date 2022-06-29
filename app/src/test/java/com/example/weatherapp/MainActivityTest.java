package com.example.weatherapp;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.content.Intent;

import com.example.weatherapp.weatherdata.CurrentWeatherData;
import com.example.weatherapp.weatherdata.ForecastWeatherData;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Test
    public void testSetCurrentWeatherDataDisplay() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            CurrentWeatherData randomCurrentWeatherData = new CurrentWeatherData
                    ("RandomCity", 100, "Weather", 105, 95, 10, 20, "01d");
            activity.setCurrentWeatherDataDisplay(randomCurrentWeatherData);
            assertEquals(activity.tvCityName.getText(), "RandomCity");
            assertEquals(activity.tvTemperatureNum.getText(), "100.0\u00B0");
            assertEquals(activity.tvWeather.getText(), "Weather");
            assertEquals(activity.tvMaxTempNum.getText(), "105.0\u00B0");
            assertEquals(activity.tvMinTempNum.getText(), "95.0\u00B0");
            assertEquals(activity.tvHumidityNum.getText(), "10.0%");
            assertEquals(activity.tvWindSpeedNum.getText(), "20.0mph");
        });
    }

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
        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ForecastWeatherData expectedForecastWeatherData = new ForecastWeatherData("2022-06-26 18:00:00", 100, "Weather", "01d");
            List<ForecastWeatherData> forecastWeatherDataList = new ArrayList<>();
            forecastWeatherDataList.add(expectedForecastWeatherData);
            activity.setForecastWeatherDataDisplay(forecastWeatherDataList);
            assertEquals(expectedForecastWeatherData, activity.getForecastListAdapter().getForecastWeatherDataList().get(0));
        });
    }
}
