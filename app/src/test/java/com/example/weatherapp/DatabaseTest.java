package com.example.weatherapp;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.weatherapp.database.FavoriteCitiesDatabase;
import com.example.weatherapp.database.FavoriteCity;
import com.example.weatherapp.database.FavoriteCityDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private FavoriteCitiesDatabase favoriteCitiesDatabase;
    private FavoriteCityDao favoriteCityDao;

    @Before
    public void setUpDatabaseAndDao() {
        favoriteCitiesDatabase = Room
                .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), FavoriteCitiesDatabase.class)
                .allowMainThreadQueries().build();
        favoriteCityDao = favoriteCitiesDatabase.favoriteCityDao();
    }

    @After
    public void closeDatabase() {
        favoriteCitiesDatabase.close();
    }

    @Test
    public void testInsertAndGet() {
        FavoriteCity insertedFavoriteCity = new FavoriteCity("London");
        long id = favoriteCityDao.insert(insertedFavoriteCity);
        FavoriteCity favoriteCity = favoriteCityDao.getByID(id);
        assertEquals(id, favoriteCity.id);
        assertEquals("London", favoriteCity.name);
    }

    @Test
    public void testUniqueIds() {
        FavoriteCity favoriteCity1 = new FavoriteCity("San Diego");
        long id1 = favoriteCityDao.insert(favoriteCity1);
        FavoriteCity favoriteCity2 = new FavoriteCity("La Jolla");
        long id2 = favoriteCityDao.insert(favoriteCity2);
        assertNotEquals(id1, id2);
    }

    @Test
    public void testGetAllNames() {
        FavoriteCity favoriteCity1 = new FavoriteCity("San Diego");
        long id1 = favoriteCityDao.insert(favoriteCity1);
        FavoriteCity favoriteCity2 = new FavoriteCity("La Jolla");
        long id2 = favoriteCityDao.insert(favoriteCity2);
        assertEquals(2, favoriteCityDao.getAllNames().size());
        assertEquals("San Diego", favoriteCityDao.getAllNames().get(0));
        assertEquals("La Jolla", favoriteCityDao.getAllNames().get(1));
    }

    @Test
    public void testDuplicateInsert() {
        FavoriteCity favoriteCity1 = new FavoriteCity("San Diego");
        long id1 = favoriteCityDao.insert(favoriteCity1);
        for (int i = 0; i < 2; ++i) {
            if (favoriteCityDao.getByName("San Diego") == null) {
                FavoriteCity favoriteCity2 = new FavoriteCity("San Diego");
                long id2 = favoriteCityDao.insert(favoriteCity2);
            }
        }
        assertEquals(1, favoriteCityDao.getAllNames().size());
    }

    @Test
    public void testDelete() {
        FavoriteCity favoriteCity = new FavoriteCity("London");
        long id = favoriteCityDao.insert(favoriteCity);
        favoriteCityDao.delete("London");
        assertNull(favoriteCityDao.getByID(id));
    }

}
