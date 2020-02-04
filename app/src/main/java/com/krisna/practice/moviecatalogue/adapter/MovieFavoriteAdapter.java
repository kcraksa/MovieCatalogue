package com.krisna.practice.moviecatalogue.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.MovieFavorite;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.GridViewHolder> {

    private List<MovieFavorite> listMovieFavorite = new ArrayList<>();
    private final Activity activity;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    private OnItemClickCallback onItemClickCallback;

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListMovieFavorite(List<MovieFavorite> listMovieFavorite) {
        Log.d("HMMM", String.valueOf(listMovieFavorite.size()));
        this.listMovieFavorite = listMovieFavorite;
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_movie, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavoriteAdapter.GridViewHolder holder, int position) {
        holder.tvTitle.setText(listMovieFavorite.get(position).getTitle());
        holder.tvVoteAverage.setText(listMovieFavorite.get(position).getRating());

        Glide.with(activity)
                .load(urlPoster + listMovieFavorite.get(position).getPoster_path())
                .centerCrop()
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listMovieFavorite.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieFavorite.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvVoteAverage;
        ImageView imgPoster;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvVoteAverage = itemView.findViewById(R.id.tv_movie_vote_average);
            imgPoster = itemView.findViewById(R.id.img_movie_poster);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieFavorite data);
    }
}
