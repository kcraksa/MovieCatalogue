package com.krisna.practice.moviecatalogue.ui.tv_show;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krisna.practice.moviecatalogue.model.TvShow;
import com.krisna.practice.moviecatalogue.services.repositories.TvShowRepository;

import java.util.Locale;

public class TvShowDetailViewModel extends ViewModel {
    private MutableLiveData<TvShow> mutableLiveData;
    private TvShowRepository tvShowRepository;
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

        tvShowRepository = TvShowRepository.getInstance();
        mutableLiveData = tvShowRepository.getTvShowDetail(tmdbId, API_KEY, language);
    }

    public LiveData<TvShow> getMovieDetailRepository() {
        return mutableLiveData;
    }
}
