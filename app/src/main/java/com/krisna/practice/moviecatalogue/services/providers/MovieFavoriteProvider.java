package com.krisna.practice.moviecatalogue.services.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.krisna.practice.moviecatalogue.services.db.FavoriteDatabase;
import com.krisna.practice.moviecatalogue.services.db.FavoriteMovieDao;

import static com.krisna.practice.moviecatalogue.services.db.DatabaseContract.AUTHORITY;
import static com.krisna.practice.moviecatalogue.services.db.DatabaseContract.MovieFavoriteColumn.TABLE_NAME;

public class MovieFavoriteProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", FAVORITE_ID);
    }

    public MovieFavoriteProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = sUriMatcher.match(uri);
        if (code == FAVORITE || code == FAVORITE_ID) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            FavoriteMovieDao favoriteMovieDao = FavoriteDatabase.getInstance(context).favoriteMovieDao();
            final Cursor cursor;
            if (code == FAVORITE) {
                cursor = favoriteMovieDao.getAllMovieFavoriteCursor();
            } else {
                cursor = favoriteMovieDao.getFavoriteMovieCursorByTmdbId(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
