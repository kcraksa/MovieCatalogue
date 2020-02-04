package com.example.moviefavorite.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefavorite.model.Movie;
import com.example.moviefavorite.model.MovieList;
import com.example.moviefavorite.services.api.ApiClient;
import com.example.moviefavorite.services.api.GetMovieDataServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository movieRepository;

    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    private GetMovieDataServices getMovieDataService;

    public MovieRepository() {
        getMovieDataService = ApiClient.createService(GetMovieDataServices.class);
    }

    public MutableLiveData<MovieList> getMovieData(String api_key, String language) {
        final MutableLiveData<MovieList> movieData = new MutableLiveData<>();

        getMovieDataService.getMovieData(api_key, language).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }

    public MutableLiveData<MovieList> getMovieNewRelease(String api_key, String language, String fromDate, String toDate) {
        final MutableLiveData<MovieList> movieData = new MutableLiveData<>();

        getMovieDataService.getMovieReleaseToday(api_key, language, fromDate, toDate).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }

    public MutableLiveData<Movie> getMovieDetail(String tmdbId, String api_key, String language) {
        final MutableLiveData<Movie> detailMovieData = new MutableLiveData<>();
        final ArrayList<Movie> movieArrayList = new ArrayList<>();

        getMovieDataService.getMovieDetail(tmdbId, api_key, language).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    detailMovieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                detailMovieData.setValue(null);
            }
        });
        return detailMovieData;
    }



    public MutableLiveData<MovieList> getMovieSearchResult(String api_key, String language, String searchParameter) {
        final MutableLiveData<MovieList> movieData = new MutableLiveData<>();

        getMovieDataService.getMovieSearchResult(api_key, language, searchParameter).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    movieData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                movieData.postValue(null);
            }
        });
        return movieData;
    }
}
