package com.krisna.practice.moviecatalogue.services.repositories;

import android.app.Application;
import android.util.Log;

import com.krisna.practice.moviecatalogue.model.TvShowFavorite;
import com.krisna.practice.moviecatalogue.services.db.FavoriteDatabase;
import com.krisna.practice.moviecatalogue.services.db.FavoriteTvShowDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TvShowFavoriteRepository {

    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<TvShowFavorite>> allFavorite;
    private MutableLiveData<Integer> searchTmdbResult = new MutableLiveData<>();
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private MutableLiveData<Integer> deleteResult = new MutableLiveData<>();

    public TvShowFavoriteRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteTvShowDao = database.favoriteTvShowDao();
        allFavorite = favoriteTvShowDao.getAllData();
    }

    public void insert(TvShowFavorite movieFavorite) {
        insertData(movieFavorite);
    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public void delete(String tmdbId) {
        deleteData(tmdbId);
    }

    public MutableLiveData<Integer> getDeleteResult() {
        return deleteResult;
    }

    public void selectByTmdbId(String tmdbId) {
        searchByTmdbAsync(tmdbId);
    }

    public LiveData<List<TvShowFavorite>> getAllFavorite() {
        return allFavorite;
    }

    public MutableLiveData<Integer> getSearchTmdbResult() {
        return searchTmdbResult;
    }

    private void insertData(final TvShowFavorite movieFavorite) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    favoriteTvShowDao.insert(movieFavorite);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                    Log.d("ERROR", e.getMessage());
                }
            }
        }).start();
    }

    private void searchByTmdbAsync(final String tmdbId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TvShowFavorite> movieFavorites;
                movieFavorites = favoriteTvShowDao.getFavoriteMovieByTmdbId(tmdbId);
                searchTmdbResult.postValue(movieFavorites.size());
            }
        }).start();
    }

    private void deleteData(final String tmdbId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    favoriteTvShowDao.deleteByTmdbId(tmdbId);
                    deleteResult.postValue(1);
                } catch (Exception e) {
                    deleteResult.postValue(0);
                }
            }
        }).start();
    }
}
