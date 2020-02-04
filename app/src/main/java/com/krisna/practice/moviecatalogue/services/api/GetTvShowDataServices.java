package com.krisna.practice.moviecatalogue.services.api;

import com.krisna.practice.moviecatalogue.model.MovieList;
import com.krisna.practice.moviecatalogue.model.TvShow;
import com.krisna.practice.moviecatalogue.model.TvShowList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetTvShowDataServices {
    @GET("discover/tv")
    Call<TvShowList> getTvShowData(@Query("api_key") String api_key, @Query("language") String language);

    @GET("tv/{id}")
    Call<TvShow> getTvShowDetail(@Path("id") String id, @Query("api_key") String api_key, @Query("language") String language);

    @GET("search/tv")
    Call<TvShowList> getTvShowSearchResult(@Query("api_key") String api_key, @Query("language") String language, @Query("query") String search);
}
