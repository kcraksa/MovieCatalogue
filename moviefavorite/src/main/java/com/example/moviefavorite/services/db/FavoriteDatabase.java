package com.example.moviefavorite.services.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviefavorite.model.MovieFavorite;

@Database(entities = {MovieFavorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static FavoriteDatabase instance;
    public abstract FavoriteMovieDao favoriteMovieDao();

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
