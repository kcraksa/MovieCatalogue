package com.krisna.practice.moviecatalogue.ui.movie;

import com.krisna.practice.moviecatalogue.model.MovieList;
import com.krisna.practice.moviecatalogue.services.repositories.MovieRepository;

import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<MovieList> listMovie;
    private MutableLiveData<MovieList> listSearchMovieResult;
    private MovieRepository movieRepository;
    private static final String API_KEY = "49c4a18c1b3a930bda11d8630c987291";
    private String current_language = Locale.getDefault().getLanguage();
    private String current_country = Locale.getDefault().getCountry();

    public void init() {
        if (listMovie != null) {
            return;
        }

        if (current_language.equals("in")) {
            current_language = "id"; // untuk menyamakan kode bahasa indonesia sistem dengan data moviedb
        }

        String language = current_language + "-" + current_country;

        movieRepository = MovieRepository.getInstance();
        listMovie = movieRepository.getMovieData(API_KEY, language);
    }

    public LiveData<MovieList> getMovieData() {
        return listMovie;
    }

    public void preparingSearchMovieData(String searchParam) {
        if (listSearchMovieResult != null) {
            return;
        }

        if (current_language.equals("in")) {
            current_language = "id"; // untuk menyamakan kode bahasa indonesia sistem dengan data moviedb
        }

        String language = current_language + "-" + current_country;

        movieRepository = MovieRepository.getInstance();
        listSearchMovieResult = movieRepository.getMovieSearchResult(API_KEY, language, searchParam);
    }

    public LiveData<MovieList> getSearchMovieResult() {
        return listSearchMovieResult;
    }
}
