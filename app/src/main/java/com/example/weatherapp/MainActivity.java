package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.helper.ImgLoader;
import com.example.weatherapp.location.LocationPermissionChecker;
import com.example.weatherapp.weatherdata.CurrentWeatherData;
import com.example.weatherapp.weatherdata.ForecastWeatherData;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Boolean value that determines whether or not to enable GPS. Used for testing.
    private boolean ENABLE_GPS;

    private MainActivityPresenter presenter;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private AppBarLayout appBarLayout;
    private EditText etSearchBar;
    private ImageButton ibSearchButton;
    private TextView tvCityName;
    private TextView tvTemperatureNum;
    private TextView tvWeather;
    private TextView tvMaxTempNum;
    private TextView tvMinTempNum;
    private TextView tvHumidityNum;
    private TextView tvWindSpeedNum;
    private ImageView ivWeatherImg;
    private RecyclerView rvForecastDataList;
    private ForecastListAdapter forecastListAdapter;

    public static final String ENABLE_GPS_INTENT = "ENABLE_GPS";
    private static final int LOCATION_REFRESH_TIME = 600000;
    private static final int LOCATION_REFRESH_DISTANCE = 0;
    private static final String FAHRENHEIT_SYMBOL = "\u00B0";
    private static final String PERCENT_SYMBOL = "%";
    private static final String MPH_SYMBOL = "mph";
    private static final String INVALID_CITY = "Invalid City";
    private static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check intent extra to determine whether or not to enable GPS. Default value is true.
        ENABLE_GPS = getIntent().getBooleanExtra(ENABLE_GPS_INTENT, true);

        setContentView(R.layout.activity_main);
        appBarLayout = findViewById(R.id.app_bar_layout);
        etSearchBar = findViewById(R.id.search_bar);
        ibSearchButton = findViewById(R.id.search_button);
        tvCityName = findViewById(R.id.city_name);
        tvTemperatureNum = findViewById(R.id.temperature_num);
        tvWeather = findViewById(R.id.weather);
        tvMaxTempNum = findViewById(R.id.max_temp_num);
        tvMinTempNum = findViewById(R.id.min_temp_num);
        tvHumidityNum = findViewById(R.id.humidity_num);
        tvWindSpeedNum = findViewById(R.id.wind_speed_num);
        ivWeatherImg = findViewById(R.id.weather_img);

        rvForecastDataList = findViewById(R.id.forecast_data_list);
        forecastListAdapter = new ForecastListAdapter(ENABLE_GPS);
        rvForecastDataList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvForecastDataList.setAdapter(forecastListAdapter);

        appBarLayout.setOutlineProvider(null);

        // Set up MVP
        MainActivityModel model = new ViewModelProvider(this).get(MainActivityModel.class);
        presenter = new MainActivityPresenter(this, model);

        findViewById(R.id.mock_location_btn).setOnClickListener(presenter::onMockButtonClicked);
        ibSearchButton.setOnClickListener(presenter::onSearchButtonClicked);
        findViewById(R.id.options_button).setOnClickListener(presenter::onOptionsMenuClicked);

        if (ENABLE_GPS) {
            disableAppBarLayout();
            LocationPermissionChecker locationPermissionChecker = new LocationPermissionChecker(this);
            locationPermissionChecker.ensurePermissions();
            // infinite loop until location permissions are granted by user
            while (!locationPermissionChecker.hasPermissions()) {
            }
            // when permissions are granted, set up location listener
            setupLocationListener();
        }
    }

    private void disableAppBarLayout() {
        appBarLayout.setEnabled(false);
        etSearchBar.setEnabled(false);
        ibSearchButton.setEnabled(false);
    }

    public void enableAppBarLayout() {
        appBarLayout.setEnabled(true);
        etSearchBar.setEnabled(true);
        ibSearchButton.setEnabled(true);
    }

    public void setCurrentWeatherDataDisplay(CurrentWeatherData currentWeatherData) {
        tvCityName.setText(currentWeatherData.getCityName());
        tvTemperatureNum.setText(currentWeatherData.getTemperature() + FAHRENHEIT_SYMBOL);
        tvWeather.setText(currentWeatherData.getWeather());
        tvMaxTempNum.setText(currentWeatherData.getMaxTemperature() + FAHRENHEIT_SYMBOL);
        tvMinTempNum.setText(currentWeatherData.getMinTemperature() + FAHRENHEIT_SYMBOL);
        tvHumidityNum.setText(currentWeatherData.getHumidity() + PERCENT_SYMBOL);
        tvWindSpeedNum.setText(currentWeatherData.getWindSpeed() + MPH_SYMBOL);
        if (ENABLE_GPS) {
            // load weather image based on weather and time of day
            ImgLoader.loadImg(currentWeatherData, ivWeatherImg);
        }
    }

    public void setForecastWeatherDataDisplay(List<ForecastWeatherData> forecastWeatherDataList) {
        forecastListAdapter.setForecastWeatherDataList(forecastWeatherDataList);
    }

    public void displayErrorMessage() {
        tvCityName.setText(INVALID_CITY);
        tvTemperatureNum.setText(EMPTY_STRING);
        tvWeather.setText(EMPTY_STRING);
        tvMaxTempNum.setText(EMPTY_STRING);
        tvMinTempNum.setText(EMPTY_STRING);
        tvHumidityNum.setText(EMPTY_STRING);
        tvWindSpeedNum.setText(EMPTY_STRING);
        ivWeatherImg.setImageResource(R.drawable.example_weather_img);
        forecastListAdapter.setForecastWeatherDataList(new ArrayList<>());
    }

    @SuppressLint("MissingPermission")
    private void setupLocationListener() {
        // Update coordinates every 10 minutes
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Pair<Double, Double> coordinates = Pair.create(
                        location.getLatitude(),
                        location.getLongitude()
                );
                presenter.updateCoordinates(coordinates);
            }
        };
        locationManager.requestLocationUpdates
                (LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener);
    }

    public void disableGPS() {
        if (ENABLE_GPS) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public void enableGPS() {
        setupLocationListener();
    }

    @VisibleForTesting
    void mockCoordinates(Pair<Double, Double> coordinates) {
        presenter.updateCoordinates(coordinates);
    }

    @VisibleForTesting
    public EditText getEtSearchBar() {
        return etSearchBar;
    }

    @VisibleForTesting
    public ImageButton getIbSearchButton() {
        return ibSearchButton;
    }

    @VisibleForTesting
    public MainActivityPresenter getPresenter() {
        return presenter;
    }

    @VisibleForTesting
    public TextView getTvCityName() {
        return tvCityName;
    }

    @VisibleForTesting
    public TextView getTvTemperatureNum() {
        return tvTemperatureNum;
    }

    @VisibleForTesting
    public TextView getTvWeather() {
        return tvWeather;
    }

    @VisibleForTesting
    public TextView getTvMaxTempNum() {
        return tvMaxTempNum;
    }

    @VisibleForTesting
    public TextView getTvMinTempNum() {
        return tvMinTempNum;
    }

    @VisibleForTesting
    public TextView getTvHumidityNum() {
        return tvHumidityNum;
    }

    @VisibleForTesting
    public TextView getTvWindSpeedNum() {
        return tvWindSpeedNum;
    }

    @VisibleForTesting
    ForecastListAdapter getForecastListAdapter() {
        return forecastListAdapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.weather_of_my_location:
                enableGPS();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}