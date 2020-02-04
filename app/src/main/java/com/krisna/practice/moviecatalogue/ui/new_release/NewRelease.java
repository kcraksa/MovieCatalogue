package com.krisna.practice.moviecatalogue.ui.new_release;

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
import com.krisna.practice.moviecatalogue.adapter.MovieAdapter;
import com.krisna.practice.moviecatalogue.model.Movie;
import com.krisna.practice.moviecatalogue.model.MovieList;
import com.krisna.practice.moviecatalogue.ui.movie.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class NewRelease extends Fragment {

    ArrayList<Movie> movieArrayList = new ArrayList<>();
    MovieAdapter movieAdapter;
    NewReleaseViewModel newReleaseViewModel;
    RecyclerView rvMovies;
    ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_release_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        rvMovies = view.findViewById(R.id.rv_new_release);
        progressBar = view.findViewById(R.id.progressBar_new_release);

        showLoading(true);

        newReleaseViewModel = ViewModelProviders.of(this).get(NewReleaseViewModel.class);
        newReleaseViewModel.init();
        newReleaseViewModel.getMovieNewRelease().observe(this, new Observer<MovieList>() {
            @Override
            public void onChanged(MovieList movieList) {
                List<Movie> movies = movieList.getResults();
                //movieArrayList.addAll(movies);
                //movieAdapter.notifyDataSetChanged();
                setFetchDataResult(movies);
                showLoading(false);
            }
        });

        movieAdapter = new MovieAdapter(getContext(), movieArrayList);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvMovies.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent detailMovie = new Intent(getContext(), MovieDetailActivity.class);
                detailMovie.putExtra(MovieDetailActivity.EXTRA_TMDB_ID, data.getId());
                startActivity(detailMovie);
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

    public void setFetchDataResult(List<Movie> movie) {
        movieArrayList.clear();
        movieArrayList.addAll(movie);
        movieAdapter.notifyDataSetChanged();
    }
}
