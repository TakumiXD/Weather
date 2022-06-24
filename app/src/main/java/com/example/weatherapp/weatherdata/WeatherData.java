package com.example.weatherapp.weatherdata;

public interface WeatherData {
    public double getTemperature();
    public void setTemperature(double temperature);
    public String getWeather();
    public void setWeather(String weather);
    public String getWeatherIcon();
    public void setWeatherIcon(String weatherIcon);
}
