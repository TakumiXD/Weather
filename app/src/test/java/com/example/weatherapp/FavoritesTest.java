package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class FavoritesTest {
    private ActivityScenario<FavoritesActivity> scenario;

    private static final String INTENT_CITY_NAMES = "city_names";
    private static final String CITY_1 = "London";
    private static final String CITY_2 = "La Jolla";
    private static final String CITY_3 = "San Diego";
    private static final int EXPECTED_ITEM_COUNT_BEFORE = 3;
    private static final int EXPECTED_ITEM_COUNT_AFTER = 2;

    @Before
    public void startActivityWithIntent() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, FavoritesActivity.class);
        ArrayList<String> favoriteCityNames = new ArrayList<>();
        favoriteCityNames.add(CITY_1);
        favoriteCityNames.add(CITY_2);
        favoriteCityNames.add(CITY_3);
        intent.putStringArrayListExtra(INTENT_CITY_NAMES, favoriteCityNames);
        scenario = ActivityScenario.<FavoritesActivity>launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void testOnCreate() {
        scenario.onActivity(activity -> {
            assertEquals(CITY_1, ((TextView)activity.getRvFavoritesList().findViewHolderForAdapterPosition(0)
                    .itemView.findViewById(R.id.favorite_city_name)).getText().toString());
            assertEquals(CITY_2, ((TextView)activity.getRvFavoritesList().findViewHolderForAdapterPosition(1)
                    .itemView.findViewById(R.id.favorite_city_name)).getText().toString());
            assertEquals(CITY_3, ((TextView)activity.getRvFavoritesList().findViewHolderForAdapterPosition(2)
                    .itemView.findViewById(R.id.favorite_city_name)).getText().toString());
        });
    }

    @Test
    public void testRemoveFavorite() {
        scenario.onActivity(activity -> {
            assertEquals(EXPECTED_ITEM_COUNT_BEFORE, activity.getFavoritesListAdapter().getItemCount());
            assertEquals(CITY_1, ((TextView)activity.getRvFavoritesList().findViewHolderForAdapterPosition(0)
                    .itemView.findViewById(R.id.favorite_city_name)).getText().toString());
            ((Button)activity.getRvFavoritesList().findViewHolderForAdapterPosition(0)
                    .itemView.findViewById(R.id.remove_favorite)).performClick();
            assertEquals(EXPECTED_ITEM_COUNT_AFTER, activity.getFavoritesListAdapter().getItemCount());
            assertNotEquals(CITY_1, ((TextView)activity.getRvFavoritesList().findViewHolderForAdapterPosition(0)
                    .itemView.findViewById(R.id.favorite_city_name)).getText().toString());
            assertTrue(activity.getRemovedCities().contains(CITY_1));
        });
    }
}
