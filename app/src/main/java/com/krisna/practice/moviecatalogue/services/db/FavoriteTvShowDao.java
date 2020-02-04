package com.krisna.practice.moviecatalogue.services.db;

import com.krisna.practice.moviecatalogue.model.TvShowFavorite;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavoriteTvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TvShowFavorite tvShowFavorite);

    @Query("DELETE FROM tv_show_favorite WHERE tmdbId = :tmdbId")
    void deleteByTmdbId(String tmdbId);

    @Delete
    void delete(TvShowFavorite movieFavorite);

    @Query("SELECT * FROM tv_show_favorite WHERE tmdbId = :tmdbId")
    List<TvShowFavorite> getFavoriteMovieByTmdbId(String tmdbId);

    @Query("SELECT * FROM tv_show_favorite ORDER BY id ASC")
    LiveData<List<TvShowFavorite>> getAllData();

    @Query("SELECT * FROM tv_show_favorite ORDER BY id ASC")
    List<TvShowFavorite> getAllMovieFavorite();
}
