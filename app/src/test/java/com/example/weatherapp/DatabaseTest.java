package com.example.weatherapp;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.weatherapp.database.FavoriteCitiesDatabase;
import com.example.weatherapp.database.FavoriteCity;
import com.example.weatherapp.database.FavoriteCityDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void testInsertAndGet() {
        FavoriteCitiesDatabase favoriteCitiesDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), FavoriteCitiesDatabase.class)
                .allowMainThreadQueries().build();
        FavoriteCityDao favoriteCityDao = favoriteCitiesDatabase.favoriteCityDao();

        FavoriteCity insertedFavoriteCity = new FavoriteCity("London");
        long id = favoriteCityDao.insert(insertedFavoriteCity);
        FavoriteCity favoriteCity = favoriteCityDao.get(id);
        assertEquals(id, favoriteCity.id);
        assertEquals("London", favoriteCity.name);

        favoriteCitiesDatabase.close();
    }

    @Test
    public void testUniqueIds() {
        FavoriteCitiesDatabase favoriteCitiesDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), FavoriteCitiesDatabase.class)
                .allowMainThreadQueries().build();
        FavoriteCityDao favoriteCityDao = favoriteCitiesDatabase.favoriteCityDao();

        FavoriteCity favoriteCity1 = new FavoriteCity("San Diego");
        long id1 = favoriteCityDao.insert(favoriteCity1);
        FavoriteCity favoriteCity2 = new FavoriteCity("La Jolla");
        long id2 = favoriteCityDao.insert(favoriteCity2);
        assertNotEquals(id1, id2);

        favoriteCitiesDatabase.close();
    }

    @Test
    public void testGetAll() {
        FavoriteCitiesDatabase favoriteCitiesDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), FavoriteCitiesDatabase.class)
                .allowMainThreadQueries().build();
        FavoriteCityDao favoriteCityDao = favoriteCitiesDatabase.favoriteCityDao();

        FavoriteCity favoriteCity1 = new FavoriteCity("San Diego");
        long id1 = favoriteCityDao.insert(favoriteCity1);
        FavoriteCity favoriteCity2 = new FavoriteCity("La Jolla");
        long id2 = favoriteCityDao.insert(favoriteCity2);
        assertEquals(2, favoriteCityDao.getAll().size());
        assertEquals("San Diego", favoriteCityDao.getAll().get(0).name);
        assertEquals("La Jolla", favoriteCityDao.getAll().get(1).name);

        favoriteCitiesDatabase.close();
    }

    @Test
    public void testDelete() {
        FavoriteCitiesDatabase favoriteCitiesDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), FavoriteCitiesDatabase.class)
                .allowMainThreadQueries().build();
        FavoriteCityDao favoriteCityDao = favoriteCitiesDatabase.favoriteCityDao();

        FavoriteCity favoriteCity = new FavoriteCity("London");
        long id = favoriteCityDao.insert(favoriteCity);
        favoriteCityDao.delete("London");
        assertNull(favoriteCityDao.get(id));

        favoriteCitiesDatabase.close();
    }

}
