package com.krisna.practice.moviecatalogue.ui.new_release;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krisna.practice.moviecatalogue.model.MovieList;
import com.krisna.practice.moviecatalogue.services.repositories.MovieRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewReleaseViewModel extends ViewModel {

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
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        movieRepository = MovieRepository.getInstance();
        listMovie = movieRepository.getMovieNewRelease(API_KEY, language, currentDate, currentDate);
    }

    public LiveData<MovieList> getMovieNewRelease() {
        return listMovie;
    }
}
