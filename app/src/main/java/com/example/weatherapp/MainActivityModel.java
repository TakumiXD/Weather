package com.example.weatherapp;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityModel extends AndroidViewModel {

    private MutableLiveData<Pair<Double, Double>> coordinates;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String weatherKey = BuildConfig.WEATHER_KEY;
    private Application application;

    public MainActivityModel(@NonNull Application application) {
        super(application);
        this.application = application;
        coordinates = new MutableLiveData<>(null);
    }

    public LiveData<Pair<Double, Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Pair<Double, Double> coordinates) {
        this.coordinates.setValue(coordinates);
        Log.d("setCoordinates", "Latitude: " + coordinates.first);
        Log.d("setCoordinates", "Longitude: " + coordinates.second);
        getData();
    }

    public void getData() {
        String fullUrl = url + "?lat=" + getCoordinates().getValue().first + "&lon=" +
                getCoordinates().getValue().second + "&appid=" + weatherKey;
        Log.d("apiresponse", fullUrl);
        RequestQueue queue = Volley.newRequestQueue(application.getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weatherInfo = response.getJSONArray("weather");
                    Log.d("apiresponse", weatherInfo.toString());
                } catch (JSONException e) {
                    Log.d("apiresponse", "failed");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("apiresponse", "error");
            }
        });
        queue.add(request);
    }
}
