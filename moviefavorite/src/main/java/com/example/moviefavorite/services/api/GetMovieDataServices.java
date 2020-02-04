package com.example.moviefavorite.services.api;

import com.example.moviefavorite.model.Movie;
import com.example.moviefavorite.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieDataServices {
    @GET("discover/movie")
    Call<MovieList> getMovieData(@Query("api_key") String api_key, @Query("language") String language);

    @GET("movie/{id}")
    Call<Movie> getMovieDetail(@Path("id") String id, @Query("api_key") String api_key, @Query("language") String language);

    @GET("search/movie")
    Call<MovieList> getMovieSearchResult(@Query("api_key") String api_key, @Query("language") String language, @Query("query") String search);

    @GET("discover/movie")
    Call<MovieList> getMovieReleaseToday(@Query("api_key") String api_key, @Query("language") String language, @Query("primary_release_date.gte") String fromDate, @Query("primary_release_date.lte") String toDate);
}
