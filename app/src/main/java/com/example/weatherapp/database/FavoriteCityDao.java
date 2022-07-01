package com.example.weatherapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FavoriteCity favoriteCity);

    @Query("SELECT * FROM `favorite_cities` WHERE `id`=:id")
    FavoriteCity getByID(long id);

    @Query("SELECT * from `favorite_cities` WHERE `name` = :name")
    FavoriteCity getByName(String name);

    @Query("SELECT name FROM `favorite_cities`")
    List<String> getAllNames();

    @Query("DELETE FROM `favorite_cities` WHERE `name`=:name")
    void delete(String name);
}
