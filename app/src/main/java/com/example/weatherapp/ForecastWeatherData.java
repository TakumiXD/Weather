package com.example.weatherapp;

public class ForecastWeatherData implements WeatherData {
    private String dateAndTime;
    private double temperature;
    private String weather;
    private String weatherIcon;

    public ForecastWeatherData(String dateAndTime, double temperature, String weather, String weatherIcon) {
        this.dateAndTime = dateAndTime;
        this.temperature = temperature;
        this.weather = weather;
        this.weatherIcon = weatherIcon;
    }

    public ForecastWeatherData(){
    }

    @Override
    public String toString() {
        return "WeatherForecastData{" +
                "dateAndTime='" + dateAndTime + '\'' +
                ", temperature=" + temperature +
                ", weather='" + weather + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                '}';
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}

