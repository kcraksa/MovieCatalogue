package com.example.moviefavorite.services.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM movie_favorite WHERE tmdbId = :tmdbId")
    Cursor getFavoriteMovieByTmdbId(long tmdbId);

    @Query("SELECT * FROM movie_favorite ORDER BY id ASC")
    Cursor getAllData();

    @Query("SELECT * FROM movie_favorite ORDER BY id ASC")
    Cursor getAllMovieFavorite();
}
