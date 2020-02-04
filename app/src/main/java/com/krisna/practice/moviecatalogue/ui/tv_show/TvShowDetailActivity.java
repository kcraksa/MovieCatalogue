package com.krisna.practice.moviecatalogue.ui.tv_show;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.TvShow;
import com.krisna.practice.moviecatalogue.model.TvShowFavorite;
import com.krisna.practice.moviecatalogue.services.db.FavoriteTvShowDao;
import com.krisna.practice.moviecatalogue.ui.favorite.FavoriteTvShowViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TMDB_ID = "EXTRA_TMDB_ID";
    public static final String EXTRA_MOVIE = "extra";
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    TvShowDetailViewModel tvShowDetailViewModel;
    static FavoriteTvShowDao favoriteTvShowDao;
    ArrayList<TvShowFavorite> tvShowFavorites = new ArrayList<>();
    private int numOfData;
    String tmdbId;
    TvShow _tvShow;

    TextView tvTitle, tvReleaseDate, tvRating, tvOverview;
    ImageView imgPoster;
    ProgressBar progressBar;
    Button btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        tmdbId = getIntent().getStringExtra(EXTRA_TMDB_ID);

        tvTitle = findViewById(R.id.tv_title_detail_tvShow);
        tvReleaseDate = findViewById(R.id.tv_releaseDate_tvShow_detail);
        tvRating = findViewById(R.id.tv_rating_tvShow_detail);
        tvOverview = findViewById(R.id.tv_synopsis_tvShow_detail);
        imgPoster = findViewById(R.id.img_detail_tvShow_poster);
        progressBar = findViewById(R.id.progressBarTvShowDetail);
        btnFavorite = findViewById(R.id.btn_tvShow_favorite);

        btnFavorite.setOnClickListener(this);

        FavoriteTvShowViewModel favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        favoriteTvShowViewModel.getSearchTmdbResult().observe(this, new Observer<Integer>() {
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
        favoriteTvShowViewModel.selectByTmdbId(tmdbId);

        showLoading(true);

        tvShowDetailViewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
        tvShowDetailViewModel.init(tmdbId);
        tvShowDetailViewModel.getMovieDetailRepository().observe(this, new Observer<TvShow>() {
            @Override
            public void onChanged(TvShow tvShow) {

                _tvShow = new TvShow(tvShow.getId(), tvShow.getName(), tvShow.getVote_average(), null, null, tvShow.getPoster_path());

                tvTitle.setText(tvShow.getName());
                tvReleaseDate.setText(formatDate(tvShow.getFirst_air_date()));
                tvRating.setText(tvShow.getVote_average());
                tvOverview.setText(tvShow.getOverview());

                Glide.with(getApplicationContext())
                        .load(urlPoster + tvShow.getPoster_path())
                        .centerCrop()
                        .into(imgPoster);

                showLoading(false);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_tvShow_favorite) {
            FavoriteTvShowViewModel favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
            favoriteTvShowViewModel.getSearchTmdbResult().observe(this, new Observer<Integer>() {

                @Override
                public void onChanged(Integer result) {
                    numOfData = result;
                }
            });
            favoriteTvShowViewModel.selectByTmdbId(tmdbId);

            if (numOfData > 0) {

                favoriteTvShowViewModel.getDeleteResult().observe(TvShowDetailActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer deleteResult) {
                        if (deleteResult == 1) {
                            Toast.makeText(TvShowDetailActivity.this, R.string.success_remove_favorite, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TvShowDetailActivity.this, R.string.fail_remove_favorite, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                favoriteTvShowViewModel.delete(tmdbId);
                favoriteTvShowViewModel.selectByTmdbId(tmdbId);

            } else {

                TvShowFavorite tvShowFavorite = new TvShowFavorite();
                tvShowFavorite.setTmdbId(tmdbId);
                tvShowFavorite.setTitle(_tvShow.getName());
                tvShowFavorite.setRating(_tvShow.getVote_average());
                tvShowFavorite.setPoster_path(_tvShow.getPoster_path());

                favoriteTvShowViewModel.getInsertResult().observe(TvShowDetailActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer insertResult) {
                        if (insertResult == 1) {
                            Toast.makeText(TvShowDetailActivity.this, R.string.success_add_favorite, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TvShowDetailActivity.this, R.string.fail_add_favorite, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                favoriteTvShowViewModel.insert(tvShowFavorite);
                favoriteTvShowViewModel.selectByTmdbId(tmdbId);
            }
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
