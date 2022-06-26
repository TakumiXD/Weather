package com.example.weatherapp.weatherdata;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.weatherapp.helper.DateTimeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    private final Context context;

    private static final String CURRENT_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String WEATHER_KEY = "98d701b935327fa1cd69560c3f8d32c0";


    public WeatherDataService(Context context) {
        this.context = context;
    }

    public void getCurrentDataByLocation(Pair<Double, Double> coordinates, VolleyResponseListener volleyResponseListener) {
        String fullUrl = CURRENT_URL + "?lat=" + coordinates.first + "&lon=" +
                coordinates.second + "&appid=" + WEATHER_KEY + "&units=imperial";

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    CurrentWeatherData currentWeatherData = new CurrentWeatherData();
                    currentWeatherData.setCityName(response.getString("name"));
                    currentWeatherData.setTemperature(response.getJSONObject("main").getDouble("temp"));
                    currentWeatherData.setWeather(response.getJSONArray("weather").getJSONObject(0).getString("main"));
                    currentWeatherData.setMaxTemperature(response.getJSONObject("main").getDouble("temp_max"));
                    currentWeatherData.setMinTemperature(response.getJSONObject("main").getDouble("temp_min"));
                    currentWeatherData.setHumidity(response.getJSONObject("main").getDouble("humidity"));
                    currentWeatherData.setWindSpeed(response.getJSONObject("wind").getDouble("speed"));
                    currentWeatherData.setWeatherIcon(response.getJSONArray("weather").getJSONObject(0).getString("icon"));
                    Log.d("WeatherApp", "JsonObjectRequest: " + currentWeatherData);
                    volleyResponseListener.onResponse(currentWeatherData, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public void getForecastByLocation(Pair<Double, Double> coordinates, VolleyResponseListener volleyResponseListener) {
        String fullUrl = FORECAST_URL + "?lat=" + coordinates.first + "&lon=" + coordinates.second +
                "&appid=" + WEATHER_KEY + "&units=imperial";

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int callSize = response.getInt("cnt");
                    List<ForecastWeatherData> forecastWeatherDataList = new ArrayList<>();
                    JSONArray list = response.getJSONArray("list");
                    for (int i = 0; i < callSize; ++i) {
                        JSONObject listObject = list.getJSONObject(i);
                        ForecastWeatherData forecastWeatherData = new ForecastWeatherData();
                        String jsonDateTime = listObject.getString("dt_txt");
                        forecastWeatherData.setDateAndTime(DateTimeConverter.jsonDateTimeToAppDateTime(jsonDateTime));
                        forecastWeatherData.setTemperature(listObject.getJSONObject("main").getDouble("temp"));
                        forecastWeatherData.setWeather(listObject.getJSONArray("weather")
                                .getJSONObject(0).getString("main"));
                        forecastWeatherData.setWeatherIcon(listObject.getJSONArray("weather")
                                .getJSONObject(0).getString("icon"));
                        forecastWeatherDataList.add(forecastWeatherData);
                        Log.d("WeatherApp", "JsonObjectRequest: " + forecastWeatherData);
                    }
                    volleyResponseListener.onResponse(null, forecastWeatherDataList);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    volleyResponseListener.onError();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

}
