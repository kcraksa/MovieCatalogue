package com.krisna.practice.moviecatalogue.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.TvShowFavorite;

import java.util.ArrayList;
import java.util.List;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.GridViewHolder>{

    private List<TvShowFavorite> listTvShowFavorite = new ArrayList<>();
    private final Activity activity;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    private OnItemClickCallback onItemClickCallback;

    public TvShowFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListTvShowFavorite(List<TvShowFavorite> listTvShowFavorite) {
        this.listTvShowFavorite = listTvShowFavorite;
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_tv_show, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowFavoriteAdapter.GridViewHolder holder, int position) {
        holder.tvTitle.setText(listTvShowFavorite.get(position).getTitle());
        holder.tvVoteAverage.setText(listTvShowFavorite.get(position).getRating());

        Glide.with(activity)
                .load(urlPoster + listTvShowFavorite.get(position).getPoster_path())
                .centerCrop()
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listTvShowFavorite.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShowFavorite.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvVoteAverage;
        ImageView imgPoster;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_tv_show_title);
            tvVoteAverage = itemView.findViewById(R.id.tv_tv_show_vote_average);
            imgPoster = itemView.findViewById(R.id.img_tv_show_poster);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShowFavorite data);
    }
}
