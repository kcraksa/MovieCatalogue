package com.krisna.practice.moviecatalogue.services.db;

import android.content.Context;

import com.krisna.practice.moviecatalogue.model.MovieFavorite;
import com.krisna.practice.moviecatalogue.model.TvShowFavorite;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieFavorite.class, TvShowFavorite.class}, version = 2, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static FavoriteDatabase instance;
    public abstract FavoriteMovieDao favoriteMovieDao();
    public abstract FavoriteTvShowDao favoriteTvShowDao();

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "db_favorite")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
