package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        findViewById(R.id.app_bar_layout).setOutlineProvider(null);

        Intent intent = getIntent();
        ArrayList<String> cityNames = intent.getStringArrayListExtra("city_names");

        RecyclerView rvFavoritesList = findViewById(R.id.favorites_list);
        FavoritesListAdapter favoritesListAdapter = new FavoritesListAdapter(this, cityNames);
        rvFavoritesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvFavoritesList.setAdapter(favoritesListAdapter);
    }

    public void onFavoriteItemClick(String cityName) {
        Intent intent = new Intent();
        intent.putExtra("result", cityName);
        setResult(RESULT_OK, intent);
        finish();
    }
}