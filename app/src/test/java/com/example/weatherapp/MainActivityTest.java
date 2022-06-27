package com.example.weatherapp;

import androidx.appcompat.view.menu.MenuView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

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
            assertEquals(activity.city_name.getText(), "RandomCity");
            assertEquals(activity.temperature_num.getText(), "100.0\u2109");
            assertEquals(activity.weather_caption.getText(), "Weather");
            assertEquals(activity.max_temp_num.getText(), "105.0\u2109");
            assertEquals(activity.min_temp_num.getText(), "95.0\u2109");
            assertEquals(activity.humidity_num.getText(), "10.0%");
            assertEquals(activity.wind_speed_num.getText(), "20.0mph");
        });
    }

//    @Test
//    public void testSetForecastWeatherDataDisplay() {
//        Context context = ApplicationProvider.getApplicationContext();
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra(MainActivity.ENABLE_GPS_INTENT, false);
//        ArrayList<String> forecastWeatherDataAL = new ArrayList<>();
//        forecastWeatherDataAL.add("2022-06-26 18:00:00");
//        forecastWeatherDataAL.add("100");
//        forecastWeatherDataAL.add("Weather");
//        forecastWeatherDataAL.add("01d");
//        intent.putExtra("doit", true);
//        intent.putExtra("array", forecastWeatherDataAL);
//        ActivityScenario<MainActivity> scenario = ActivityScenario.<MainActivity>launch(intent);
//        scenario.moveToState(Lifecycle.State.CREATED);
//
//        scenario.onActivity(activity -> {
//            RecyclerView.ViewHolder viewHolder = activity.forecast_data_list.findViewHolderForAdapterPosition(0);
//            View view = activity.getForecastDataList().getLayoutManager().findViewByPosition(0);
//            assertEquals(((TextView)view.findViewById(R.id.recycler_date_and_time)).getText(), "Sunday 5 PM");
//            assertEquals(((TextView)viewHolder.itemView.findViewById(R.id.recycler_temperature)).getText(), "100.0\u2109");
//            assertEquals(((TextView)viewHolder.itemView.findViewById(R.id.recycler_weather)).getText(), "Weather");
//        });
//
////        ForecastWeatherData forecastWeatherData = new ForecastWeatherData("2022-06-26 18:00:00", 100, "Weather", "01d");
////        List<ForecastWeatherData> forecastWeatherDataList = new ArrayList<>();
////        forecastWeatherDataList.add(forecastWeatherData);
////        activity.setForecastWeatherDataDisplay(forecastWeatherDataList);
////        RecyclerView.ViewHolder viewHolder = activity.forecast_data_list.findViewHolderForAdapterPosition(0);
////        View view = activity.getForecastDataList().getLayoutManager().findViewByPosition(0);
////        assertEquals(((TextView)view.findViewById(R.id.recycler_date_and_time)).getText(), "Sunday 5 PM");
//////            assertEquals(((TextView)viewHolder.itemView.findViewById(R.id.recycler_temperature)).getText(), "100.0\u2109");
//////            assertEquals(((TextView)viewHolder.itemView.findViewById(R.id.recycler_weather)).getText(), "Weather");
//    }
}
