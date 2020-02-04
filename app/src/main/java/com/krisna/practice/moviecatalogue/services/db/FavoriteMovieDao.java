package com.krisna.practice.moviecatalogue.services.db;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.krisna.practice.moviecatalogue.model.MovieFavorite;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieFavorite movieFavorite);

    @Query("DELETE FROM movie_favorite WHERE tmdbId = :tmdbId")
    void deleteByTmdbId(String tmdbId);

    @Delete
    void delete(MovieFavorite movieFavorite);

    @Query("SELECT * FROM movie_favorite WHERE tmdbId = :tmdbId")
    List<MovieFavorite> getFavoriteMovieByTmdbId(String tmdbId);

    @Query("SELECT * FROM movie_favorite WHERE tmdbId = :tmdbId")
    Cursor getFavoriteMovieCursorByTmdbId(long tmdbId);

    @Query("SELECT * FROM movie_favorite ORDER BY id ASC")
    LiveData<List<MovieFavorite>> getAllData();

    @Query("SELECT * FROM movie_favorite ORDER BY id ASC")
    List<MovieFavorite> getAllMovieFavorite();

    @Query("SELECT * FROM movie_favorite ORDER BY id ASC")
    Cursor getAllMovieFavoriteCursor();
}
