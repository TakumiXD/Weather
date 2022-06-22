package com.example.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import java.util.Arrays;

public class LocationPermissionChecker {
    private final ComponentActivity activity;

    final ActivityResultLauncher<String[]> requestPermissionLauncher;

    private final String[] requiredPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public LocationPermissionChecker(ComponentActivity activity) {
        this.activity = activity;
        requestPermissionLauncher = activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), perms -> {
            perms.forEach((perm, isGranted) -> {
                Log.d("WeatherApp", "Permission" + perm + "granted:" + isGranted);
            });
        });
    }

    public void ensurePermissions() {
        boolean hasNoLocationPerms = Arrays.stream(requiredPermissions)
                .map(permission -> ContextCompat.checkSelfPermission(activity, permission))
                .allMatch(status -> status == PackageManager.PERMISSION_DENIED);

        if (hasNoLocationPerms) {
            requestPermissionLauncher.launch(requiredPermissions);
        }
    }

    public boolean hasPermissions() {
        return (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
}
