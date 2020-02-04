package com.krisna.practice.moviecatalogue.ui.movie;

import com.krisna.practice.moviecatalogue.model.Movie;
import com.krisna.practice.moviecatalogue.services.repositories.MovieRepository;

import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailViewModel extends ViewModel {

    private MutableLiveData<Movie> mutableLiveData;
    private MovieRepository movieRepository;
    private static final String API_KEY = "49c4a18c1b3a930bda11d8630c987291";
    private String current_language = Locale.getDefault().getLanguage();
    private String current_country = Locale.getDefault().getCountry();

    public void init(String tmdbId) {
        if (mutableLiveData != null) {
            return;
        }

        if (current_language.equals("in")) {
            current_language = "id"; // untuk menyamakan kode bahasa indonesia sistem dengan data moviedb
        }

        String language = current_language + "-" + current_country;

        movieRepository = MovieRepository.getInstance();
        mutableLiveData = movieRepository.getMovieDetail(tmdbId, API_KEY, language);
    }

    public LiveData<Movie> getMovieDetailRepository() {
        return mutableLiveData;
    }
}
