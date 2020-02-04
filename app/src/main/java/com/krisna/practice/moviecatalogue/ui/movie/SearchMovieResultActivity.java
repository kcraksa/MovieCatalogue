package com.krisna.practice.moviecatalogue.ui.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.krisna.practice.moviecatalogue.MainActivity;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.Movie;
import com.krisna.practice.moviecatalogue.model.MovieList;
import com.krisna.practice.moviecatalogue.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieResultActivity extends AppCompatActivity {

    public final static String EXTRA_QUERY = "extra_query";
    private int numOfResult = 0;

    ArrayList<Movie> searchResultArrayList = new ArrayList<>();
    MovieAdapter searchAdapter;
    MovieViewModel mViewModel;
    RecyclerView rvSearchView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        rvSearchView = findViewById(R.id.rv_movies_search_result);
        progressBar = findViewById(R.id.progressBarSearchResult);

        showLoading(true);

        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mViewModel.preparingSearchMovieData(getIntent().getStringExtra(EXTRA_QUERY));
        mViewModel.getSearchMovieResult().observe(this, new Observer<MovieList>() {
            @Override
            public void onChanged(MovieList movieList) {
                List<Movie> movies = movieList.getResults();
                searchResultArrayList.addAll(movies);
                searchAdapter.notifyDataSetChanged();
                showLoading(false);
            }
        });

        searchAdapter = new MovieAdapter(this, searchResultArrayList);
        rvSearchView.setHasFixedSize(true);
        rvSearchView.setLayoutManager(new GridLayoutManager(this, 2));
        rvSearchView.setAdapter(searchAdapter);

        searchAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent detailMovie = new Intent(getApplicationContext(), MovieDetailActivity.class);
                detailMovie.putExtra(MovieDetailActivity.EXTRA_TMDB_ID, data.getId());
                startActivity(detailMovie);
            }
        });

        numOfResult = searchResultArrayList.size();
        setTitle("Search Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
