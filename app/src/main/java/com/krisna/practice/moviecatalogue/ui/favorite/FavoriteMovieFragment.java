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
import com.krisna.practice.moviecatalogue.adapter.MovieFavoriteAdapter;
import com.krisna.practice.moviecatalogue.model.MovieFavorite;
import com.krisna.practice.moviecatalogue.ui.movie.MovieDetailActivity;
import com.krisna.practice.moviecatalogue.ui.movie.MovieDetailViewModel;

import java.util.List;

public class FavoriteMovieFragment extends Fragment {

    MovieFavoriteAdapter movieFavoriteAdapter;
    private ProgressBar progressBar;
    private RecyclerView rvMovieFavorite;
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private MovieDetailViewModel detailMovieViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarMovieFavorite);
        rvMovieFavorite = view.findViewById(R.id.rv_movieFavorite);
        rvMovieFavorite.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvMovieFavorite.setHasFixedSize(true);
        movieFavoriteAdapter = new MovieFavoriteAdapter(getActivity());
        rvMovieFavorite.setAdapter(movieFavoriteAdapter);

        movieFavoriteAdapter.setOnItemClickCallback(new MovieFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieFavorite data) {

                Intent detailMovie = new Intent(getContext(), MovieDetailActivity.class);
                detailMovie.putExtra(MovieDetailActivity.EXTRA_TMDB_ID, data.getTmdbId());
                startActivity(detailMovie);

            }
        });

        showLoading(true);
        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllMovieFavorite().observe(this, new Observer<List<MovieFavorite>>() {
            @Override
            public void onChanged(List<MovieFavorite> movieFavorites) {
                movieFavoriteAdapter.setListMovieFavorite(movieFavorites);
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
