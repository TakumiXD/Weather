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
    private static final String INTENT_ENABLE_GPS = "ENABLE_GPS";
    private static final String INTENT_USE_DATABASE = "USE_DATABASE";
    private static final String CITY = "London";
    private static final String WEATHER = "Clear";
    private static final double TEMPERATURE = 100;
    private static final String TEMPERATURE_STRING = "100.0";
    private static final double MAX_TEMPERATURE = 105;
    private static final String MAX_TEMPERATURE_STRING = "105.0";
    private static final double MIN_TEMPERATURE = 95;
    private static final String MIN_TEMPERATURE_STRING = "95.0";
    private static final double HUMIDITY = 10;
    private static final String HUMIDITY_STRING = "10.0";
    private static final double WIND_SPEED = 20;
    private static final String WIND_SPEED_STRING = "20.0";
    private static final String WEATHER_ICON = "01d";
    private static final String DEGREE_SYMBOL = "\u00B0";
    private static final String PERCENT_SYMBOL = "%";
    private static final String MPH_SYMBOL = "mph";

    @Test
    public void testSetCurrentWeatherDataDisplay() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(INTENT_ENABLE_GPS, false);
        intent.putExtra(INTENT_USE_DATABASE, false);
        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            CurrentWeatherData mockCurrentWeatherData = new CurrentWeatherData
                    (CITY, TEMPERATURE, WEATHER, MAX_TEMPERATURE, MIN_TEMPERATURE, HUMIDITY, WIND_SPEED, WEATHER_ICON);
            activity.setCurrentWeatherDataDisplay(mockCurrentWeatherData);
            assertEquals(CITY, activity.getTvCityName().getText());
            assertEquals(TEMPERATURE_STRING + DEGREE_SYMBOL, activity.getTvTemperatureNum().getText());
            assertEquals(WEATHER, activity.getTvWeather().getText());
            assertEquals(MAX_TEMPERATURE_STRING + DEGREE_SYMBOL, activity.getTvMaxTempNum().getText());
            assertEquals(MIN_TEMPERATURE_STRING + DEGREE_SYMBOL, activity.getTvMinTempNum().getText());
            assertEquals(HUMIDITY_STRING + PERCENT_SYMBOL, activity.getTvHumidityNum().getText());
            assertEquals(WIND_SPEED_STRING + MPH_SYMBOL, activity.getTvWindSpeedNum().getText());
        });
    }
}
