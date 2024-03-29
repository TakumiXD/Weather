package com.example.weatherapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
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

    // Boolean value that determines whether or not to use a real GPS. Used for testing.
    private boolean USE_GPS;
    // Boolean value that determines whether or not to use a real database. Used for testing.
    private boolean USE_DATABASE;

    private MainActivityPresenter presenter;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private AppBarLayout appBarLayout;
    private EditText etSearchBar;
    private ImageButton ibSearchButton;
    private ImageButton ibMoreOptions;
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

    private static final boolean DEFAULT_VALUE = true;
    private static final String INTENT_ENABLE_GPS = "ENABLE_GPS";
    private static final String INTENT_USE_DATABASE = "USE_DATABASE";
    private static final int LOCATION_REFRESH_TIME = 600000;
    private static final int LOCATION_REFRESH_DISTANCE = 0;
    private static final int START = 0;
    private static final String DEGREE_SYMBOL = "\u00B0";
    private static final String PERCENT_SYMBOL = "%";
    private static final String MPH_SYMBOL = "mph";
    private static final String INVALID_CITY = "Invalid City";
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String INTENT_CITY_NAMES = "city_names";
    private static final String INTENT_RESULT = "result";
    private static final String INTENT_REMOVED = "removed";
    private static final String LOG_MAIN_TAG = "WeatherApp";

    private final ActivityResultLauncher<Intent> startForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d(LOG_MAIN_TAG, "Result was RESULT_OK");
                        String resultString = result.getData().getStringExtra(INTENT_RESULT);
                        if (resultString != null) {
                            presenter.updateSearchedWeatherData(resultString);
                        }
                        ArrayList removedCities = result.getData().getStringArrayListExtra(INTENT_REMOVED);
                        if ((removedCities != null) && (!removedCities.isEmpty())) {
                            presenter.updateFavoriteCityNames(removedCities);
                        }
                    }
                    else {
                        Log.d(LOG_MAIN_TAG, "Result was not RESULT_OK");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check intent extra to determine whether or not to use GPS and database.
        USE_GPS = getIntent().getBooleanExtra(INTENT_ENABLE_GPS, DEFAULT_VALUE);
        boolean USE_DATABASE = getIntent().getBooleanExtra(INTENT_USE_DATABASE, DEFAULT_VALUE);

        setContentView(R.layout.activity_main);
        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setOutlineProvider(null);
        etSearchBar = findViewById(R.id.search_bar);
        ibSearchButton = findViewById(R.id.search_button);
        ibMoreOptions = findViewById(R.id.options_button);
        tvCityName = findViewById(R.id.city_name);
        tvTemperatureNum = findViewById(R.id.temperature_num);
        tvWeather = findViewById(R.id.weather);
        tvMaxTempNum = findViewById(R.id.max_temp_num);
        tvMinTempNum = findViewById(R.id.min_temp_num);
        tvHumidityNum = findViewById(R.id.humidity_num);
        tvWindSpeedNum = findViewById(R.id.wind_speed_num);
        ivWeatherImg = findViewById(R.id.weather_img);
        // When the search key on the keyboard is clicked
        etSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.onSearchButtonClicked(v);
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        rvForecastDataList = findViewById(R.id.forecast_data_list);
        forecastListAdapter = new ForecastListAdapter(USE_GPS);
        rvForecastDataList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvForecastDataList.setAdapter(forecastListAdapter);

        // Set up MVP
        MainActivityModel model = new ViewModelProvider(this).get(MainActivityModel.class);
        presenter = new MainActivityPresenter(this, model, USE_DATABASE);

        findViewById(R.id.mock_location_btn).setOnClickListener(presenter::onMockButtonClicked);
        ibSearchButton.setOnClickListener(presenter::onSearchButtonClicked);
        ibMoreOptions.setOnClickListener(presenter::onOptionsMenuClicked);

        if (USE_GPS) {
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

    public void openFavoritesList(ArrayList<String> favoriteCityNames) {
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        intent.putStringArrayListExtra(INTENT_CITY_NAMES, favoriteCityNames);
        startForResult.launch(intent);
    }

    private void disableAppBarLayout() {
        appBarLayout.setEnabled(false);
        etSearchBar.setEnabled(false);
        ibSearchButton.setEnabled(false);
        ibMoreOptions.setEnabled(false);
    }

    public void enableAppBarLayout() {
        appBarLayout.setEnabled(true);
        etSearchBar.setEnabled(true);
        ibSearchButton.setEnabled(true);
        ibMoreOptions.setEnabled(true);
    }

    public void setCurrentWeatherDataDisplay(CurrentWeatherData currentWeatherData) {
        tvCityName.setText(currentWeatherData.getCityName());
        tvTemperatureNum.setText(currentWeatherData.getTemperature() + DEGREE_SYMBOL);
        tvWeather.setText(currentWeatherData.getWeather());
        tvMaxTempNum.setText(currentWeatherData.getMaxTemperature() + DEGREE_SYMBOL);
        tvMinTempNum.setText(currentWeatherData.getMinTemperature() + DEGREE_SYMBOL);
        tvHumidityNum.setText(currentWeatherData.getHumidity() + PERCENT_SYMBOL);
        tvWindSpeedNum.setText(currentWeatherData.getWindSpeed() + SPACE + MPH_SYMBOL);
        if (USE_GPS) {
            // load weather image based on weather and time of day
            ImgLoader.loadImg(currentWeatherData, ivWeatherImg);
        }
    }

    public void setForecastWeatherDataDisplay(List<ForecastWeatherData> forecastWeatherDataList) {
        rvForecastDataList.scrollToPosition(START);
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
        if (USE_GPS) {
            Log.d(LOG_MAIN_TAG, "GPS disabled");
            locationManager.removeUpdates(locationListener);
        }
    }

    public void enableGPS() {
        Log.d(LOG_MAIN_TAG, "GPS enabled");
        setupLocationListener();
    }

    @VisibleForTesting
    void mockCoordinates(Pair<Double, Double> coordinates) {
        presenter.updateCoordinates(coordinates);
    }

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
}