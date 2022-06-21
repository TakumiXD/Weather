package com.example.weatherapp;

public interface VolleyResponseListener {
    void onResponse(CurrentWeatherData currentWeatherData);
    void onError();
}
