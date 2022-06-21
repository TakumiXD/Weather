package com.example.weatherapp;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;
    private final String imgURLHead = "https://openweathermap.org/img/wn/";
    private final String imgURLEnd = "@2x.png";
    public boolean TESTING = false;
    public TextView city_name;
    public TextView temperature_num;
    public TextView weather_caption;
    public TextView max_temp_num;
    public TextView min_temp_num;
    public TextView humidity_num;
    public TextView wind_speed_num;
    public ImageView weather_img;
    //testpush

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Weather");
        setContentView(R.layout.activity_main);

        MainActivityModel model = new ViewModelProvider(this).get(MainActivityModel.class);
        presenter = new MainActivityPresenter(this, model);

        findViewById(R.id.mock_location_btn).setOnClickListener(presenter::onMockButtonClicked);

        city_name = findViewById(R.id.city_name);
        temperature_num = findViewById(R.id.temperature_num);
        weather_caption = findViewById(R.id.weather_caption);
        max_temp_num = findViewById(R.id.max_temp_num);
        min_temp_num = findViewById(R.id.min_temp_num);
        humidity_num = findViewById(R.id.humidity_num);
        wind_speed_num = findViewById(R.id.wind_speed_num);
        weather_img = findViewById(R.id.weather_img);
    }

    public void setCurrentWeatherDataDisplay(CurrentWeatherData currentWeatherData) {
        city_name.setText(currentWeatherData.getCityName());
        temperature_num.setText("" + currentWeatherData.getTemperature() + "\u2109");
        weather_caption.setText(currentWeatherData.getWeather());
        max_temp_num.setText("" + currentWeatherData.getMaxTemperature() + "\u2109");
        min_temp_num.setText("" + currentWeatherData.getMinTemperature() + "\u2109");
        humidity_num.setText("" + currentWeatherData.getHumidity() + "%");
        wind_speed_num.setText(currentWeatherData.getWindSpeed() + "mph");
        if (!TESTING) {
            String fullURL = imgURLHead + currentWeatherData.getWeatherIcon() + imgURLEnd;
            Picasso.get().load(fullURL).into(weather_img);
        }
    }

}