package com.example.weatherapp;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.database.FavoriteCitiesDatabase;
import com.example.weatherapp.database.FavoriteCity;
import com.example.weatherapp.database.FavoriteCityDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivityModel extends AndroidViewModel {

    private final MutableLiveData<Pair<Double, Double>> coordinates;
    private final Application application;
    private ArrayList<String> favoriteCityNames;
    private FavoriteCityDao favoriteCityDao;

    private static final String LOG_SET_COORDINATES_TAG = "setCoordinates";
    private static final String LOG_SET_FAVORITE_CITY_NAMES_TAG = "setFavoriteCityNames";

    public MainActivityModel(@NonNull Application application) {
        super(application);
        this.application = application;
        coordinates = new MutableLiveData<>(null);
    }

    public void makeDatabase() {
        favoriteCityDao = FavoriteCitiesDatabase.getSingleton(application.getApplicationContext()).favoriteCityDao();
        List<String> favoriteCityNames = favoriteCityDao.getAllNames();
        ArrayList<String> favoriteCityNamesAL = new ArrayList<>();
        favoriteCityNamesAL.addAll(favoriteCityNames);
        setFavoriteCityNames(favoriteCityNamesAL);
    }

    public LiveData<Pair<Double, Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Pair<Double, Double> coordinates) {
        this.coordinates.setValue(coordinates);
        Log.d(LOG_SET_COORDINATES_TAG, "Latitude: " + coordinates.first);
        Log.d(LOG_SET_COORDINATES_TAG, "Longitude: " + coordinates.second);
    }

    public ArrayList<String> getFavoriteCityNames() {
        return favoriteCityNames;
    }

    public void setFavoriteCityNames(ArrayList<String> favoriteCityNames) {
        for (String favoriteCityName : favoriteCityNames) {
            Log.d(LOG_SET_FAVORITE_CITY_NAMES_TAG, "Favorite City: " + favoriteCityName);
        }
        this.favoriteCityNames = favoriteCityNames;
    }

    public void addFavoriteCity(String favoriteCityName) {
        if (favoriteCityDao.getByName(favoriteCityName) == null) {
            favoriteCityNames.add(favoriteCityName);
            favoriteCityDao.insert(new FavoriteCity(favoriteCityName));
        }
    }

    public void removeFavoriteCities(ArrayList<String> cityNames) {
        for (String cityName : cityNames) {
            favoriteCityNames.remove(cityName);
            favoriteCityDao.delete(cityName);
        }
    }

}
