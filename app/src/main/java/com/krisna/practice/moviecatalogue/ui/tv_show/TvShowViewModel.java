package com.krisna.practice.moviecatalogue.ui.tv_show;

import com.krisna.practice.moviecatalogue.model.TvShowList;
import com.krisna.practice.moviecatalogue.services.repositories.TvShowRepository;

import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TvShowList> listTvShow;
    private MutableLiveData<TvShowList> listSearchTvShowResult;
    private TvShowRepository tvShowRepository;
    private static final String API_KEY = "49c4a18c1b3a930bda11d8630c987291";
    private String current_language = Locale.getDefault().getLanguage();
    private String current_country = Locale.getDefault().getCountry();

    public void init() {
        if (listTvShow != null) {
            return;
        }

        if (current_language.equals("in")) {
            current_language = "id"; // untuk menyamakan kode bahasa indonesia sistem dengan data moviedb
        }

        String language = current_language + "-" + current_country;

        tvShowRepository = TvShowRepository.getInstance();
        listTvShow = tvShowRepository.getTvShowData(API_KEY, language);
    }

    public LiveData<TvShowList> getMovieData() {
        return listTvShow;
    }

    public void preparingSearchMovieData(String searchParam) {
        if (listSearchTvShowResult != null) {
            return;
        }

        if (current_language.equals("in")) {
            current_language = "id"; // untuk menyamakan kode bahasa indonesia sistem dengan data moviedb
        }

        String language = current_language + "-" + current_country;

        tvShowRepository = TvShowRepository.getInstance();
        listSearchTvShowResult = tvShowRepository.getTvShowSearchResult(API_KEY, language, searchParam);
    }

    public LiveData<TvShowList> getSearchMovieResult() {
        return listSearchTvShowResult;
    }
}
