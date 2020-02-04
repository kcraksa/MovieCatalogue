package com.example.moviefavorite;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefavorite.adapter.MovieFavoriteAdapter;
import com.example.moviefavorite.model.DatabaseContract;
import com.example.moviefavorite.model.MovieFavorite;
import com.example.moviefavorite.util.MappingHelper;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieFavoriteCallback {

    private ProgressBar progressBar;
    private RecyclerView rvMovies;
    private MovieFavoriteAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Movie Favorite");

        progressBar = findViewById(R.id.progressBar);
        rvMovies = findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setHasFixedSize(true);
        adapter = new MovieFavoriteAdapter(this);
        rvMovies.setAdapter(adapter);

        adapter.setOnItemClickCallback(new MovieFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieFavorite data) {
                Intent detailMovie = new Intent(getApplicationContext(), MovieDetailActivity.class);
                detailMovie.putExtra(MovieDetailActivity.EXTRA_TMDB_ID, data.getTmdbId());
                startActivity(detailMovie);
            }
        });

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.MovieFavoriteColumn.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadNoteAsync(this, this).execute();
        } else {
            ArrayList<MovieFavorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovieFavorite(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovieFavorites());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieFavorite> movieFavorites) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movieFavorites.size() > 0) {
            adapter.setListMovieFavorite(movieFavorites);
        } else {
            adapter.setListMovieFavorite(new ArrayList<MovieFavorite>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<MovieFavorite>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieFavoriteCallback> weakCallback;

        private LoadNoteAsync(Context context, LoadMovieFavoriteCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieFavorite> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.MovieFavoriteColumn.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieFavorite> movieFavorites) {
            super.onPostExecute(movieFavorites);
            weakCallback.get().postExecute(movieFavorites);
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovies, message, Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNoteAsync(context, (LoadMovieFavoriteCallback) context).execute();
        }
    }
}

interface LoadMovieFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<MovieFavorite> movieFavorites);
}
