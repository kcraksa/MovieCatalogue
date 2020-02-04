package com.example.moviefavorite.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.krisna.practice.moviecatalogue";
    private static final String SCHEME = "content";

    public static final class MovieFavoriteColumn implements BaseColumns {

        public static final String TABLE_NAME = "movie_favorite";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
