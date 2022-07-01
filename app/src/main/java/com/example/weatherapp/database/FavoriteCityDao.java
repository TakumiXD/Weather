package com.example.weatherapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface FavoriteCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FavoriteCity favoriteCity);

    @Transaction
    @Query("SELECT * FROM `favorite_cities` WHERE `id`=:id")
    FavoriteCity getByID(long id);

    @Transaction
    @Query("SELECT * from `favorite_cities` WHERE `name` = :name")
    FavoriteCity getByName(String name);

    @Transaction
    @Query("SELECT name FROM `favorite_cities`")
    List<String> getAllNames();

    @Query("DELETE FROM `favorite_cities` WHERE `name`=:name")
    void delete(String name);
}
