package com.example.moviefavorite.util;

import android.database.Cursor;

import com.example.moviefavorite.model.MovieFavorite;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<MovieFavorite> mapCursorToArrayList(Cursor movieFavoriteCursor) {
        ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();

        while (movieFavoriteCursor.moveToNext()) {

            int id = movieFavoriteCursor.getInt(movieFavoriteCursor.getColumnIndexOrThrow("id"));
            String tmdbId = movieFavoriteCursor.getString(movieFavoriteCursor.getColumnIndexOrThrow("tmdbId"));
            String title = movieFavoriteCursor.getString(movieFavoriteCursor.getColumnIndexOrThrow("title"));
            String rating = movieFavoriteCursor.getString(movieFavoriteCursor.getColumnIndexOrThrow("rating"));
            String poster_path = movieFavoriteCursor.getString(movieFavoriteCursor.getColumnIndexOrThrow("poster_path"));
            movieFavoriteArrayList.add(new MovieFavorite(id, tmdbId, title, rating, poster_path));
        }

        return movieFavoriteArrayList;
    }
}
