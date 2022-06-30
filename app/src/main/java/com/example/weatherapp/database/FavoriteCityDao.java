package com.example.weatherapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteCityDao {
    @Insert
    long insert(FavoriteCity favoriteCity);

    @Query("SELECT * FROM `favorite_cities` WHERE `id`=:id")
    FavoriteCity get (long id);

    @Query("SELECT * FROM `favorite_cities`")
    List<FavoriteCity> getAll();

    @Query("DELETE FROM `favorite_cities` WHERE `name`=:name")
    void delete(String name);
}
