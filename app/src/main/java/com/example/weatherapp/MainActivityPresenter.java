package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivityPresenter {

    private final MainActivity activity;
    private final MainActivityModel model;

    public MainActivityPresenter(MainActivity activity, MainActivityModel model) {
        this.activity = activity;
        this.model = model;
    }

    public void updateCoordinates(Pair<Double, Double> coordinates) {
        model.setCoordinates(coordinates);
    }

    @SuppressLint("SetTextI18n")
    public void onMockButtonClicked(View view) {
        var inputType = EditorInfo.TYPE_CLASS_NUMBER
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

        var builder = new AlertDialog.Builder(activity)
                .setTitle("Mock Coordinates")
                .setView(layout)
                .setPositiveButton("Submit", (dialog, which) -> {
                    var lat = Double.parseDouble(latInput.getText().toString());
                    var lng = Double.parseDouble(lngInput.getText().toString());
                    updateCoordinates(Pair.create(lat, lng));
                    updateUserLocationWeatherData();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
        builder.show();
    }

    public void updateUserLocationWeatherData() {
        WeatherDataService weatherDataService = new WeatherDataService(activity);
        weatherDataService.getCurrentDataByLocation(model.getCoordinates(), new VolleyResponseListener() {
            @Override
            public void onResponse(CurrentWeatherData currentWeatherData) {
                Log.d("MainActivityPresenter", "WeatherDataService.getCurrentData success");
                activity.setCurrentWeatherDataDisplay(currentWeatherData);
            }

            @Override
            public void onError() {
                Log.d("MainActivityPresenter", "WeatherDataService.getCurrentData failed");
            }
        });
    }

}
