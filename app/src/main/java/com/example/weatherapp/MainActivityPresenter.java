package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ResourceCursorAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class MainActivityPresenter {

    private final MainActivity activity;
    private final MainActivityModel model;

    public MainActivityPresenter(MainActivity activity, MainActivityModel model) {
        this.activity = activity;
        this.model = model;
    }

    public void updateCoordinates(Pair<Double, Double> coordinates) {
        model.setCoordinates(coordinates);
    }

    @SuppressLint("SetTextI18n")
    public void onMockButtonClicked(View view) {
        var inputType = EditorInfo.TYPE_CLASS_NUMBER
                | EditorInfo.TYPE_NUMBER_FLAG_SIGNED
                | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;

        final EditText latInput = new EditText(activity);
        latInput.setInputType(inputType);
        latInput.setHint("-90 <= Latitude <= 90");

        final EditText lngInput = new EditText(activity);
        lngInput.setInputType(inputType);
        lngInput.setHint("180 <= Longitude <= 180");

        final LinearLayout layout = new LinearLayout(activity);
        layout.setDividerPadding(8);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(latInput);
        layout.addView(lngInput);

        var builder = new AlertDialog.Builder(activity)
                .setTitle("Inject a Mock Location")
                .setView(layout)
                .setPositiveButton("Submit", (dialog, which) -> {
                    var lat = Double.parseDouble(latInput.getText().toString());
                    var lng = Double.parseDouble(lngInput.getText().toString());
                    updateCoordinates(Pair.create(lat, lng));
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
        builder.show();
    }

}
