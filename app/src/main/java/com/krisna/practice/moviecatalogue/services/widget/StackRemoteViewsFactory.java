package com.krisna.practice.moviecatalogue.services.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.krisna.practice.moviecatalogue.MovieFavoriteWidget;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.Movie;
import com.krisna.practice.moviecatalogue.model.MovieFavorite;
import com.krisna.practice.moviecatalogue.services.db.FavoriteDatabase;
import com.krisna.practice.moviecatalogue.services.db.FavoriteMovieDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<MovieFavorite> listMovieFavorite = new ArrayList<>();
    private final Context mContext;
    private FavoriteDatabase database;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        database = FavoriteDatabase.getInstance(mContext.getApplicationContext());
    }

    @Override
    public void onDataSetChanged() {

        FavoriteMovieDao dao = database.favoriteMovieDao();
        List<MovieFavorite> list = dao.getAllMovieFavorite();
        listMovieFavorite.addAll(list);

//        new AsyncTask<Context, Void, List<MovieFavorite>>() {
//            @Override
//            protected List<MovieFavorite> doInBackground(Context... contexts) {
//                List<MovieFavorite> list;
//                list = dao.getAllMovieFavorite();
//                return list;
//            }
//
//            @Override
//            protected void onPostExecute(List<MovieFavorite> movieFavorites) {
//                super.onPostExecute(movieFavorites);
//                if (movieFavorites != null) {
//                    listMovieFavorite.addAll(movieFavorites);
//                }
//            }
//        }.execute();
    }

    @Override
    public void onDestroy() {
        database.close();
    }

    @Override
    public int getCount() {
        return listMovieFavorite.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        final FavoriteMovieDao dao = database.favoriteMovieDao();

//        Log.d("FETCH_DATA", listMovieFavorite.get(i).getPoster_path());
        try {
            Bitmap bitmap = Glide.with(mContext)
                                    .asBitmap()
                                    .load(urlPoster + listMovieFavorite.get(i).getPoster_path())
                                    .apply(new RequestOptions().fitCenter())
                                    .submit(800, 550)
                                    .get();

            remoteViews.setImageViewBitmap(R.id.imageViewMovieWidget, bitmap);

            Log.d("FETCH_DATA", listMovieFavorite.get(i).getPoster_path());
        } catch (Exception e) {
            Log.d("FETCH_DATA", e.getMessage());
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(MovieFavoriteWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageViewMovieWidget, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
