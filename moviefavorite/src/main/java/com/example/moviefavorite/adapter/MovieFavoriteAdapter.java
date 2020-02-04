package com.example.moviefavorite.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviefavorite.R;
import com.example.moviefavorite.model.MovieFavorite;

import java.util.ArrayList;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.GridViewHolder> {

    private ArrayList<MovieFavorite> listMovieFavorite = new ArrayList<>();
    private final Activity activity;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    private OnItemClickCallback onItemClickCallback;

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieFavorite> getListMovieFavorites() {
        return listMovieFavorite;
    }

    public void setListMovieFavorite(ArrayList<MovieFavorite> listMovieFavorite) {
        this.listMovieFavorite.clear();
        this.listMovieFavorite.addAll(listMovieFavorite);
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

        holder.tvTitle.setText(getListMovieFavorites().get(position).getTitle());
        holder.tvVoteAverage.setText(getListMovieFavorites().get(position).getRating());

        Glide.with(activity)
                .load(urlPoster + getListMovieFavorites().get(position).getPoster_path())
                .centerCrop()
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(getListMovieFavorites().get(holder.getAdapterPosition()));
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
