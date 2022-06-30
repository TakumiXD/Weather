package com.example.weatherapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_cities")
public class FavoriteCity {

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    @NonNull
    public String name;

    FavoriteCity(@NonNull String name) {
        this.name = name;
    }
}
