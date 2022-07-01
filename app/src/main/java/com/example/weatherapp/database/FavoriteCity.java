package com.example.weatherapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_cities")
public class FavoriteCity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id = 0;

    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    public FavoriteCity(@NonNull String name) {
        this.name = name;
    }
}
