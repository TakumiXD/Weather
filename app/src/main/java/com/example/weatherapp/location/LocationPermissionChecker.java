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

    private final String[] requiredPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public LocationPermissionChecker(ComponentActivity activity) {
        this.activity = activity;
        requestPermissionLauncher = activity.registerForActivityResult
                (new ActivityResultContracts.RequestMultiplePermissions(), perms -> {
            perms.forEach((perm, isGranted) -> {
                Log.d("WeatherApp", "Permission" + perm + "granted:" + isGranted);
            });
        });
    }

    public void ensurePermissions() {
        if (!hasPermissions()) {
            requestPermissionLauncher.launch(requiredPermissions);
        }
    }

    public boolean hasPermissions() {
        return (activity.checkSelfPermission
                (Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (activity.checkSelfPermission
                (Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
}
