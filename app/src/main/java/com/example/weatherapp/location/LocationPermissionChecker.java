package com.example.weatherapp.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class LocationPermissionChecker {
    private final ComponentActivity activity;
    private final ActivityResultLauncher<String[]> requestPermissionLauncher;

    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final String LOG_MAIN_TAG = "WeatherApp";

    public LocationPermissionChecker(ComponentActivity activity) {
        this.activity = activity;
        requestPermissionLauncher = activity.registerForActivityResult
                (new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
            permissions.forEach((permission, isGranted) -> {
                Log.d("WeatherApp", "Permission" + permission + "granted:" + isGranted);
            });
        });
    }

    public void ensurePermissions() {
        if (!hasPermissions()) {
            Log.d(LOG_MAIN_TAG, "Lack permissions, permissions must be granted");
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS);
        }
        else {
            Log.d(LOG_MAIN_TAG, "Has permissions");
        }
    }

    public boolean hasPermissions() {
        return (activity.checkSelfPermission
                (Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (activity.checkSelfPermission
                (Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
}
