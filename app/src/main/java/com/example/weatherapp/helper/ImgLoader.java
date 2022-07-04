package com.example.weatherapp.helper;

import android.widget.ImageView;

import com.example.weatherapp.weatherdata.WeatherData;
import com.squareup.picasso.Picasso;

public class ImgLoader {
    private static final String IMG_URL_HEAD = "https://openweathermap.org/img/wn/";
    private static final String IMG_URL_TAIL = "@2x.png";

    // Load an image using url from the OpenWeatherMap website
    public static void loadImg(WeatherData weatherData, ImageView imageView) {
        String fullURL = IMG_URL_HEAD + weatherData.getWeatherIcon() + IMG_URL_TAIL;
        Picasso.get().load(fullURL).into(imageView);
    }
}
