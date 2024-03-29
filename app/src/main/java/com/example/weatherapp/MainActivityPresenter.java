package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.example.weatherapp.weatherdata.CurrentWeatherData;
import com.example.weatherapp.weatherdata.ForecastWeatherData;
import com.example.weatherapp.weatherdata.VolleyResponseListener;
import com.example.weatherapp.weatherdata.WeatherDataService;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter {

    private final MainActivity activity;
    private final MainActivityModel model;

    public String currentSearchedCityName = "";

    public MainActivityPresenter(MainActivity activity, MainActivityModel model, boolean useDatabase) {
        this.activity = activity;
        this.model = model;
        if (useDatabase) {
            model.makeDatabase();
        }
    }

    public void updateCoordinates(Pair<Double, Double> coordinates) {
        model.setCoordinates(coordinates);
        updateUserLocationWeatherData();
    }

    public void updateFavoriteCityNames(ArrayList<String> removedCityNames) {
        model.removeFavoriteCities(removedCityNames);
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
        activity.disableGPS();
        activity.getEtSearchBar().clearFocus();
        // Hide the keyboard
        InputMethodManager inputMethodManager =
                (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getEtSearchBar().getWindowToken(), 0);
        String searchBarText = activity.getEtSearchBar().getText().toString();
        updateSearchedWeatherData(searchBarText);
        Log.d("WeatherApp", "Search Button  with: " + searchBarText);
    }

    public void onOptionsMenuClicked(View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.options_menu, popup.getMenu());
        popup.setOnMenuItemClickListener( menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.see_favorites:
                    onSeeFavoritesMenuItemClicked();
                    return true;
                case R.id.add_to_favorites:
                    onAddToFavoritesMenuItemClicked();
                    return true;
                case R.id.weather_of_my_location:
                    onWeatherOfMyLocationMenuItemClicked();
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    private void onSeeFavoritesMenuItemClicked() {
        activity.openFavoritesList(model.getFavoriteCityNames());
        Log.d("WeatherApp", "menu item clicked: See Favorites");
    }

    private void onAddToFavoritesMenuItemClicked() {
        model.addFavoriteCity(activity.getTvCityName().getText().toString());
        Log.d("WeatherApp", "menu item clicked: Add to Favorites");
    }

    private void onWeatherOfMyLocationMenuItemClicked() {
        Log.d("WeatherApp", "menu item clicked: Weather of my Location");
        activity.enableGPS();
    }

    private void updateUserLocationWeatherData() {
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

    public void updateSearchedWeatherData(@NonNull String cityName) {
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

    @VisibleForTesting
    public MainActivityModel getModel() {
        return model;
    }
}
