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
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.helper.ImgLoader;
import com.example.weatherapp.location.LocationPermissionChecker;
import com.example.weatherapp.weatherdata.CurrentWeatherData;
import com.example.weatherapp.weatherdata.ForecastWeatherData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;
    private LocationPermissionChecker locationPermissionChecker;

    private static final String IMG_URL_HEAD = "https://openweathermap.org/img/wn/";
    private static final String IMG_URL_TAIL = "@2x.png";
    public static final String ENABLE_GPS_INTENT = "ENABLE_GPS";
    private static final int LOCATION_REFRESH_TIME = 600000;
    private static final int LOCATION_REFRESH_DISTANCE = 0;

    public TextView city_name;
    public TextView temperature_num;
    public TextView weather_caption;
    public TextView max_temp_num;
    public TextView min_temp_num;
    public TextView humidity_num;
    public TextView wind_speed_num;
    public ImageView weather_img;
    private RecyclerView forecast_data_list;

    // Boolean value that determines whether or not to enable GPS. Used for testing.
    private boolean ENABLE_GPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Weather");
        setContentView(R.layout.activity_main);
        city_name = findViewById(R.id.city_name);
        temperature_num = findViewById(R.id.temperature_num);
        weather_caption = findViewById(R.id.weather_caption);
        max_temp_num = findViewById(R.id.max_temp_num);
        min_temp_num = findViewById(R.id.min_temp_num);
        humidity_num = findViewById(R.id.humidity_num);
        wind_speed_num = findViewById(R.id.wind_speed_num);
        weather_img = findViewById(R.id.weather_img);
        forecast_data_list = findViewById(R.id.forecast_data_list);
        forecast_data_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Check intent extra to determine whether or not to enable GPS. Default value is true.
        ENABLE_GPS = getIntent().getBooleanExtra(ENABLE_GPS_INTENT, true);

        // Set up MVP
        MainActivityModel model = new ViewModelProvider(this).get(MainActivityModel.class);
        presenter = new MainActivityPresenter(this, model);

        findViewById(R.id.mock_location_btn).setOnClickListener(presenter::onMockButtonClicked);

        if (ENABLE_GPS) {
            locationPermissionChecker = new LocationPermissionChecker(this);
            locationPermissionChecker.ensurePermissions();
            // infinite loop until location permissions are granted by user
            while (!locationPermissionChecker.hasPermissions()) {
            }
            // when permissions are granted, set up location listener
            setupLocationListener();
        }
    }

    public void setCurrentWeatherDataDisplay(CurrentWeatherData currentWeatherData) {
        city_name.setText(currentWeatherData.getCityName());
        temperature_num.setText("" + currentWeatherData.getTemperature() + "\u2109");
        weather_caption.setText(currentWeatherData.getWeather());
        max_temp_num.setText("" + currentWeatherData.getMaxTemperature() + "\u2109");
        min_temp_num.setText("" + currentWeatherData.getMinTemperature() + "\u2109");
        humidity_num.setText("" + currentWeatherData.getHumidity() + "%");
        wind_speed_num.setText(currentWeatherData.getWindSpeed() + "mph");
        if (ENABLE_GPS) {
            // load weather image based on weather and time of day
            ImgLoader.loadImg(currentWeatherData, weather_img);
        }
    }

    public void setForecastWeatherDataDisplay(List<ForecastWeatherData> forecastWeatherDataList) {
        ForecastListAdapter adapter = new ForecastListAdapter(forecastWeatherDataList);
        adapter.setHasStableIds(true);
        forecast_data_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        forecast_data_list.setAdapter(adapter);
    }

    @SuppressLint("MissingPermission")
    private void setupLocationListener() {
        // Update coordinates every 10 minutes
        LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
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

    @VisibleForTesting
    void mockCoordinates(Pair<Double, Double> coordinates) {
        presenter.updateCoordinates(coordinates);
    }

    @VisibleForTesting
    RecyclerView getForecastDataList() {
        return forecast_data_list;
    }

}