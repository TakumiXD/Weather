package com.example.weatherapp.weatherdata;

import java.util.List;

public interface VolleyResponseListener {
    void onResponse(CurrentWeatherData currentWeatherData,
                    List<ForecastWeatherData> forecastWeatherDataList);
    void onError();
}
