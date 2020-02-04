package com.krisna.practice.moviecatalogue.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krisna.practice.moviecatalogue.model.TvShowFavorite;
import com.krisna.practice.moviecatalogue.services.repositories.TvShowFavoriteRepository;

import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private TvShowFavoriteRepository tvShowFavoriteRepository;
    private LiveData<List<TvShowFavorite>> allTvShowResult;
    private MutableLiveData<Integer> searchTmdbResult;
    private MutableLiveData<Integer> insertResult;
    private MutableLiveData<Integer> deleteResult;

    public FavoriteTvShowViewModel(@NonNull Application application) {
        super(application);
        tvShowFavoriteRepository = new TvShowFavoriteRepository(application);
        allTvShowResult = tvShowFavoriteRepository.getAllFavorite();
        searchTmdbResult = tvShowFavoriteRepository.getSearchTmdbResult();
        insertResult = tvShowFavoriteRepository.getInsertResult();
        deleteResult = tvShowFavoriteRepository.getDeleteResult();
    }

    public void insert(TvShowFavorite movieFavorite) {
        tvShowFavoriteRepository.insert(movieFavorite);
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public void delete(String tmdbId) {
        tvShowFavoriteRepository.delete(tmdbId);
    }

    public LiveData<Integer> getDeleteResult() {
        return deleteResult;
    }

    public void selectByTmdbId(String tmdbId) {
        tvShowFavoriteRepository.selectByTmdbId(tmdbId);
    }

    public LiveData<Integer> getSearchTmdbResult() {
        return searchTmdbResult;
    }

    public LiveData<List<TvShowFavorite>> getAllTvShowFavorite() {
        return allTvShowResult;
    }
}
