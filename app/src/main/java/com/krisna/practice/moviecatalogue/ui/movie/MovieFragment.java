package com.krisna.practice.moviecatalogue.ui.movie;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.adapter.MovieAdapter;
import com.krisna.practice.moviecatalogue.model.Movie;
import com.krisna.practice.moviecatalogue.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    private SearchView.OnQueryTextListener queryTextListener;

    ArrayList<Movie> movieArrayList = new ArrayList<>();
    MovieAdapter movieAdapter;
    MovieViewModel mViewModel;
    RecyclerView rvMovies;
    ProgressBar progressBar;
    SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        rvMovies = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar);

        showLoading(true);

        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mViewModel.init();
        mViewModel.getMovieData().observe(this, new Observer<MovieList>() {
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intent = new Intent(getContext(), SearchMovieResultActivity.class);
                    intent.putExtra(SearchMovieResultActivity.EXTRA_QUERY, query);
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
             default:
                    break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
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
