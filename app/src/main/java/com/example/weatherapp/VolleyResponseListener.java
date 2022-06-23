package com.example.weatherapp;

public interface VolleyResponseListener {
    void onResponse(WeatherData weatherData);
    void onError();
}
