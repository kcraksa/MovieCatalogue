package com.example.moviefavorite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.moviefavorite.model.Movie;
import com.example.moviefavorite.model.MovieFavorite;
import com.example.moviefavorite.services.db.FavoriteMovieDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra";
    public static final String EXTRA_TMDB_ID = "extra_tmdb_id";
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    static FavoriteMovieDao favoriteMovieDao;
    ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();
    private int numOfData;
    String tmdbId;
    Movie movie;

    MovieDetailViewModel detailMovieViewModel;

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
