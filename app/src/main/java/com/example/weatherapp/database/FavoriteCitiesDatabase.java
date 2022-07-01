package com.example.weatherapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities= {FavoriteCity.class}, version=1)
public abstract class FavoriteCitiesDatabase extends RoomDatabase {
    private static FavoriteCitiesDatabase singleton = null;

    public abstract FavoriteCityDao favoriteCityDao();

    public synchronized static FavoriteCitiesDatabase getSingleton(Context context){
        if(singleton == null){
            singleton = FavoriteCitiesDatabase.makeDatabase(context);
        }
        return singleton;
    }
    private static FavoriteCitiesDatabase makeDatabase(Context context){
        return Room.databaseBuilder(context, FavoriteCitiesDatabase.class, "favorite_cities.db")
                .allowMainThreadQueries().build();
    }
}
