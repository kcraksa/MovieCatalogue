package com.krisna.practice.moviecatalogue.services.repositories;

import android.util.Log;

import com.krisna.practice.moviecatalogue.model.TvShow;
import com.krisna.practice.moviecatalogue.model.TvShowList;
import com.krisna.practice.moviecatalogue.services.api.ApiClient;
import com.krisna.practice.moviecatalogue.services.api.GetTvShowDataServices;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository {

    private static TvShowRepository tvShowRepository;

    public static TvShowRepository getInstance() {
        if (tvShowRepository == null) {
            tvShowRepository = new TvShowRepository();
        }
        return tvShowRepository;
    }

    private GetTvShowDataServices getTvShowDataServices;

    public TvShowRepository() {
        getTvShowDataServices = ApiClient.createService(GetTvShowDataServices.class);
    }

    public MutableLiveData<TvShowList> getTvShowData(String api_key, String language) {
        final MutableLiveData<TvShowList> tvShowData = new MutableLiveData<>();

        getTvShowDataServices.getTvShowData(api_key, language).enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                if (response.isSuccessful()) {
                    tvShowData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvShowList> call, Throwable t) {
                tvShowData.setValue(null);
            }
        });
        return tvShowData;
    }

    public MutableLiveData<TvShow> getTvShowDetail(String tmdbId, String api_key, String language) {
        final MutableLiveData<TvShow> detailTvShow = new MutableLiveData<>();
        final ArrayList<TvShow> tvShowArrayList = new ArrayList<>();

        getTvShowDataServices.getTvShowDetail(tmdbId, api_key, language).enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                Log.d("TV_SHOW_DETAIL", response.body().getName());
                if (response.isSuccessful()) {
                    detailTvShow.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                detailTvShow.setValue(null);
            }
        });
        return detailTvShow;
    }

    public MutableLiveData<TvShowList> getTvShowSearchResult(String api_key, String language, String searchParameter) {
        final MutableLiveData<TvShowList> tvShowData = new MutableLiveData<>();

        getTvShowDataServices.getTvShowSearchResult(api_key, language, searchParameter).enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        Log.d("HASIL", response.body().getResults().get(i).getName());
                    }
                    tvShowData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvShowList> call, Throwable t) {
                Log.d("HASIL", t.getMessage());
                tvShowData.postValue(null);
            }
        });
        return tvShowData;
    }
}
