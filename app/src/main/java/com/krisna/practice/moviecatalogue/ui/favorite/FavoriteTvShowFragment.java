package com.krisna.practice.moviecatalogue.ui.favorite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.adapter.TvShowFavoriteAdapter;
import com.krisna.practice.moviecatalogue.model.TvShow;
import com.krisna.practice.moviecatalogue.model.TvShowFavorite;
import com.krisna.practice.moviecatalogue.ui.tv_show.TvShowDetailActivity;
import com.krisna.practice.moviecatalogue.ui.tv_show.TvShowDetailViewModel;

import java.util.List;

public class FavoriteTvShowFragment extends Fragment {

    TvShowFavoriteAdapter tvShowFavoriteAdapter;
    private ProgressBar progressBar;
    private RecyclerView rvTvShowFavorite;
    private FavoriteTvShowViewModel favoriteTvShowViewModel;
    private TvShowDetailViewModel detailMovieViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_tv_show_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarTvShowFavorite);
        rvTvShowFavorite = view.findViewById(R.id.rv_tvShowFavorite);
        rvTvShowFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvTvShowFavorite.setHasFixedSize(true);
        tvShowFavoriteAdapter = new TvShowFavoriteAdapter(getActivity());
        rvTvShowFavorite.setAdapter(tvShowFavoriteAdapter);

        tvShowFavoriteAdapter.setOnItemClickCallback(new TvShowFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowFavorite data) {

                Intent detailMovie = new Intent(getContext(), TvShowDetailActivity.class);
                detailMovie.putExtra(TvShowDetailActivity.EXTRA_TMDB_ID, data.getTmdbId());
                startActivity(detailMovie);

            }
        });

        showLoading(true);
        favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        favoriteTvShowViewModel.getAllTvShowFavorite().observe(this, new Observer<List<TvShowFavorite>>() {
            @Override
            public void onChanged(List<TvShowFavorite> tvShowFavorites) {
                tvShowFavoriteAdapter.setListTvShowFavorite(tvShowFavorites);
                showLoading(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
