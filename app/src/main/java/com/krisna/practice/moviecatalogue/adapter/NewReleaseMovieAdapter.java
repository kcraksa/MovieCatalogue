package com.krisna.practice.moviecatalogue.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krisna.practice.moviecatalogue.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewReleaseMovieAdapter extends RecyclerView.Adapter<NewReleaseMovieAdapter.GridViewHolder> {

    private ArrayList<Movie> dataList;
    private OnItemClickCallback onItemClickCallback;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    Context context;

    public NewReleaseMovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.dataList = movieArrayList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public NewReleaseMovieAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NewReleaseMovieAdapter.GridViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
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

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}
