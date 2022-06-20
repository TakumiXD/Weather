package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;
    private Button mockLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Weather");
        setContentView(R.layout.activity_main);

        MainActivityModel model = new ViewModelProvider(this).get(MainActivityModel.class);
        presenter = new MainActivityPresenter(this, model);

        mockLocationBtn = findViewById(R.id.mock_location_btn);
        mockLocationBtn.setOnClickListener(presenter::onMockButtonClicked);
    }
}