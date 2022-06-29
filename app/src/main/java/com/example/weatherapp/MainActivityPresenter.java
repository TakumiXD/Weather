package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.VisibleForTesting;

import com.example.weatherapp.weatherdata.CurrentWeatherData;
import com.example.weatherapp.weatherdata.ForecastWeatherData;
import com.example.weatherapp.weatherdata.VolleyResponseListener;
import com.example.weatherapp.weatherdata.WeatherDataService;

import java.util.List;

public class MainActivityPresenter {

    private final MainActivity activity;
    private final MainActivityModel model;

    public String currentSearchedCityName = "";

    public MainActivityPresenter(MainActivity activity, MainActivityModel model) {
        this.activity = activity;
        this.model = model;
    }

    public void updateCoordinates(Pair<Double, Double> coordinates) {
        model.setCoordinates(coordinates);
        updateUserLocationWeatherData();
    }

    @SuppressLint("SetTextI18n")
    public void onMockButtonClicked(View view) {
        int inputType = EditorInfo.TYPE_CLASS_NUMBER
                | EditorInfo.TYPE_NUMBER_FLAG_SIGNED
                | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;

        final EditText latInput = new EditText(activity);
        latInput.setInputType(inputType);
        latInput.setHint("Latitude: [-90,90]");

        final EditText lngInput = new EditText(activity);
        lngInput.setInputType(inputType);
        lngInput.setHint("Longitude: [-180,180]");

        final LinearLayout layout = new LinearLayout(activity);
        layout.setDividerPadding(8);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(latInput);
        layout.addView(lngInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("Mock Coordinates")
                .setView(layout)
                .setPositiveButton("Submit", (dialog, which) -> {
                    Double lat = Double.parseDouble(latInput.getText().toString());
                    Double lng = Double.parseDouble(lngInput.getText().toString());
                    updateCoordinates(Pair.create(lat, lng));
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
        builder.show();
    }

    public void onSearchButtonClicked(View view) {
        String searchBarText = activity.getEtSearchBar().getText().toString();
        activity.disableGPS();
        updateSearchedWeatherData(searchBarText);
        Log.d("WeatherApp", "Search Button  with: " + searchBarText);
    }

    public void updateUserLocationWeatherData() {
        WeatherDataService weatherDataService = new WeatherDataService(activity);
        weatherDataService.getCurrentDataByLocation(model.getCoordinates().getValue(), new VolleyResponseListener() {
            @Override
            public void onResponse(CurrentWeatherData currentWeatherData,
                                   List<ForecastWeatherData> forecastWeatherDataList) {
                Log.d("WeatherApp", "WeatherDataService.getCurrentData success");
                activity.setCurrentWeatherDataDisplay(currentWeatherData);
            }

            @Override
            public void onError() {
                Log.d("WeatherApp", "WeatherDataService.getCurrentData failed");
            }
        });
        weatherDataService.getForecastDataByLocation(model.getCoordinates().getValue(), new VolleyResponseListener() {
            @Override
            public void onResponse(CurrentWeatherData currentWeatherData,
                                   List<ForecastWeatherData> forecastWeatherDataList) {
                Log.d("WeatherApp", "WeatherDataService.getForecast success");
                activity.setForecastWeatherDataDisplay(forecastWeatherDataList);
            }

            @Override
            public void onError() {
                Log.d("WeatherApp", "WeatherDataService.getForecast failed");
            }
        });
        activity.enableAppBarLayout();
    }

    public void updateSearchedWeatherData(String cityName) {
        currentSearchedCityName = cityName;
        WeatherDataService weatherDataService = new WeatherDataService(activity);
        weatherDataService.getCurrentDataByCityName(cityName, new VolleyResponseListener() {
            @Override
            public void onResponse(CurrentWeatherData currentWeatherData, List<ForecastWeatherData> forecastWeatherDataList) {
                activity.setCurrentWeatherDataDisplay(currentWeatherData);
                Log.d("WeatherApp", "WeatherDataService.getCurrentData success");
            }
            @Override
            public void onError() {
                activity.displayErrorMessage();
                Log.d("WeatherApp", "WeatherDataService.getCurrentData failed");
            }
        });
        weatherDataService.getForecastDataByCityName(cityName, new VolleyResponseListener() {
            @Override
            public void onResponse(CurrentWeatherData currentWeatherData, List<ForecastWeatherData> forecastWeatherDataList) {
                Log.d("WeatherApp", "WeatherDataService.getForecast success");
                activity.setForecastWeatherDataDisplay(forecastWeatherDataList);
            }
            @Override
            public void onError() {
                Log.d("WeatherApp", "WeatherDataService.getForecast failed");
            }
        });
    }

    @VisibleForTesting
    public String getCurrentSearchedCityName() {
        return currentSearchedCityName;
    }
}
