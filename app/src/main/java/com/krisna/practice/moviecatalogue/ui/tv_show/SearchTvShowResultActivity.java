package com.krisna.practice.moviecatalogue.ui.tv_show;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.adapter.TvShowAdapter;
import com.krisna.practice.moviecatalogue.model.TvShow;
import com.krisna.practice.moviecatalogue.model.TvShowList;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchTvShowResultActivity extends AppCompatActivity {
    public static final String EXTRA_QUERY = "EXTRA_QUERY";
    private int numOfResult = 0;

    ArrayList<TvShow> searchResultArrayList = new ArrayList<>();
    TvShowAdapter searchAdapter;
    TvShowViewModel mViewModel;
    RecyclerView rvSearchView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        rvSearchView = findViewById(R.id.rv_movies_search_result);
        progressBar = findViewById(R.id.progressBarSearchResult);

        showLoading(true);

        mViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        mViewModel.preparingSearchMovieData(getIntent().getStringExtra(EXTRA_QUERY));
        mViewModel.getSearchMovieResult().observe(this, new Observer<TvShowList>() {
            @Override
            public void onChanged(TvShowList tvShowList) {
                List<TvShow> tvShows = tvShowList.getResults();
                searchResultArrayList.addAll(tvShows);
                searchAdapter.notifyDataSetChanged();
                showLoading(false);
            }
        });

        searchAdapter = new TvShowAdapter(this, searchResultArrayList);
        rvSearchView.setHasFixedSize(true);
        rvSearchView.setLayoutManager(new GridLayoutManager(this, 2));
        rvSearchView.setAdapter(searchAdapter);

        searchAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent detailTvShow = new Intent(getApplicationContext(), TvShowDetailActivity.class);
                detailTvShow.putExtra(TvShowDetailActivity.EXTRA_TMDB_ID, data.getId());
                startActivity(detailTvShow);
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
