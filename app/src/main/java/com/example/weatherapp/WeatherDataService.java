package com.example.weatherapp;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    private Context context;
    private static final String url = "https://api.openweathermap.org/data/2.5/weather";
    private static final String weatherKey = "98d701b935327fa1cd69560c3f8d32c0";

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public void getCurrentDataByLocation(Pair<Double, Double> coordinates, VolleyResponseListener volleyResponseListener) {
        String fullUrl = url + "?lat=" + coordinates.first + "&lon=" +
                coordinates.second + "&appid=" + weatherKey + "&units=imperial";
        Log.d("WeatherApp", "Weather API Response: " + fullUrl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
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
                    Log.d("WeatherApp", "JsonObjectRequest: " + currentWeatherData.toString());
                    volleyResponseListener.onResponse(currentWeatherData);
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

}
