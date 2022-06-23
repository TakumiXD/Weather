package com.example.weatherapp;

public class CurrentWeatherData implements WeatherData {
    private String cityName;
    private double temperature;
    private String weather;
    private double maxTemperature;
    private double minTemperature;
    private double humidity;
    private double windSpeed;
    private String weatherIcon;

    public CurrentWeatherData(String cityName, double temperature, String weather, double maxTemperature, double minTemperature, double humidity, double windSpeed, String weatherIcon) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.weather = weather;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherIcon = weatherIcon;
    }

    public CurrentWeatherData() {
    }

    @Override
    public String toString() {
        return "CurrentWeatherData{" +
                "cityName='" + cityName + '\'' +
                ", temperature=" + temperature +
                ", weather='" + weather + '\'' +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", weatherIcon=" + weatherIcon +
                '}';
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
