package com.krisna.practice.moviecatalogue.ui.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.krisna.practice.moviecatalogue.MovieFavoriteWidget;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.Movie;
import com.krisna.practice.moviecatalogue.model.MovieFavorite;
import com.krisna.practice.moviecatalogue.services.db.FavoriteMovieDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra";
    public static final String EXTRA_TMDB_ID = "extra_tmdb_id";
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    MovieDetailViewModel detailMovieViewModel;
    static FavoriteMovieDao favoriteMovieDao;
    ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();
    private int numOfData;
    String tmdbId;
    Movie movie;

    TextView tvTitle, tvReleaseDate, tvRating, tvOverview;
    ImageView imgPoster;
    ProgressBar progressBar;
    Button btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tmdbId = getIntent().getStringExtra(EXTRA_TMDB_ID);

        tvTitle = findViewById(R.id.tv_title_detail_movie);
        tvReleaseDate = findViewById(R.id.tv_releaseDate_movie_detail);
        tvRating = findViewById(R.id.tv_rating_movie_detail);
        tvOverview = findViewById(R.id.tv_synopsis_movie_detail);
        imgPoster = findViewById(R.id.img_detail_movie_poster);
        progressBar = findViewById(R.id.progressBarMovieDetail);
        btnFavorite = findViewById(R.id.btn_movie_favorite);

        btnFavorite.setOnClickListener(this);

        FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getSearchTmdbResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer result) {
                if (result > 0) {
                    btnFavorite.setBackgroundColor(Color.RED);
                    btnFavorite.setText(getResources().getString(R.string.remove_favorite));
                } else {
                    btnFavorite.setBackgroundColor(getResources().getColor(R.color.orangeButton));
                    btnFavorite.setText(getResources().getString(R.string.add_to_favorite));
                }
            }
        });
        favoriteMovieViewModel.selectByTmdbId(tmdbId);

        showLoading(true);

        detailMovieViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        detailMovieViewModel.init(tmdbId);
        detailMovieViewModel.getMovieDetailRepository().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movies) {

                movie = new Movie(movies.getId(), movies.getTitle(), movies.getVote_average(), null, null, movies.getPoster_path());

                tvTitle.setText(movies.getTitle());
                tvReleaseDate.setText(formatDate(movies.getRelease_date()));
                tvRating.setText(movies.getVote_average());
                tvOverview.setText(movies.getOverview());

                Glide.with(getApplicationContext())
                        .load(urlPoster + movies.getPoster_path())
                        .centerCrop()
                        .into(imgPoster);

                showLoading(false);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_movie_favorite) {
            FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
            favoriteMovieViewModel.getSearchTmdbResult().observe(this, new Observer<Integer>() {

                @Override
                public void onChanged(Integer result) {
                    numOfData = result;
                }
            });
            favoriteMovieViewModel.selectByTmdbId(tmdbId);

            if (numOfData > 0) {

                favoriteMovieViewModel.getDeleteResult().observe(MovieDetailActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer deleteResult) {
                        if (deleteResult == 1) {
                            Toast.makeText(MovieDetailActivity.this, R.string.success_remove_favorite, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MovieDetailActivity.this, R.string.fail_remove_favorite, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                favoriteMovieViewModel.delete(tmdbId);
                favoriteMovieViewModel.selectByTmdbId(tmdbId);

            } else {

                MovieFavorite movieFavorite = new MovieFavorite();
                movieFavorite.setTmdbId(tmdbId);
                movieFavorite.setTitle(movie.getTitle());
                movieFavorite.setRating(movie.getVote_average());
                movieFavorite.setPoster_path(movie.getPoster_path());

                favoriteMovieViewModel.getInsertResult().observe(MovieDetailActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer insertResult) {
                        if (insertResult == 1) {
                            Toast.makeText(MovieDetailActivity.this, R.string.success_add_favorite, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MovieDetailActivity.this, R.string.fail_add_favorite, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                favoriteMovieViewModel.insert(movieFavorite);
                favoriteMovieViewModel.selectByTmdbId(tmdbId);
            }

            Intent intent = new Intent(this, MovieFavoriteWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
            // since it seems the onUpdate() is only fired on that:
            int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MovieFavoriteWidget.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(intent);
        }
    }

    private String formatDate(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;
        String result = null;

        try {
            newDate = inputFormat.parse(date);
            result = outputFormat.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
