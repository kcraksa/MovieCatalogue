package com.krisna.practice.moviecatalogue.ui.favorite;

import android.app.Application;

import com.krisna.practice.moviecatalogue.model.MovieFavorite;
import com.krisna.practice.moviecatalogue.services.repositories.MovieFavoriteRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private MovieFavoriteRepository movieFavoriteRepository;
    private LiveData<List<MovieFavorite>> allMovieFavorite;
    private MutableLiveData<Integer> searchTmdbResult;
    private MutableLiveData<Integer> insertResult;
    private MutableLiveData<Integer> deleteResult;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieFavoriteRepository = new MovieFavoriteRepository(application);
        allMovieFavorite = movieFavoriteRepository.getAllFavorite();
        searchTmdbResult = movieFavoriteRepository.getSearchTmdbResult();
        insertResult = movieFavoriteRepository.getInsertResult();
        deleteResult = movieFavoriteRepository.getDeleteResult();
    }

    public void insert(MovieFavorite movieFavorite) {
        movieFavoriteRepository.insert(movieFavorite);
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public void delete(String tmdbId) {
        movieFavoriteRepository.delete(tmdbId);
    }

    public LiveData<Integer> getDeleteResult() {
        return deleteResult;
    }

    public void selectByTmdbId(String tmdbId) {
        movieFavoriteRepository.selectByTmdbId(tmdbId);
    }

    public LiveData<Integer> getSearchTmdbResult() {
        return searchTmdbResult;
    }

    public LiveData<List<MovieFavorite>> getAllMovieFavorite() {
        return allMovieFavorite;
    }
}
