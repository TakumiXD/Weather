package com.example.weatherapp;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainActivityModel extends AndroidViewModel {

    private final MutableLiveData<Pair<Double, Double>> coordinates;

    private ArrayList<String> favoriteCityNames;

    public MainActivityModel(@NonNull Application application) {
        super(application);
        coordinates = new MutableLiveData<>(null);
    }

    public LiveData<Pair<Double, Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Pair<Double, Double> coordinates) {
        this.coordinates.setValue(coordinates);
        Log.d("setCoordinates", "Latitude: " + coordinates.first);
        Log.d("setCoordinates", "Longitude: " + coordinates.second);
    }

    public ArrayList<String> getFavoriteCityNames() {
        return favoriteCityNames;
    }

    public void setFavoriteCityNames(ArrayList<String> favoriteCityNames) {
        for (String favoriteCityName : favoriteCityNames) {
            Log.d("setFavoriteCityNames", "Favorite City: " + favoriteCityName);
        }
        this.favoriteCityNames = favoriteCityNames;
    }
}
