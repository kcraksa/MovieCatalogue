package com.krisna.practice.moviecatalogue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.GridViewHolder> {

    private ArrayList<Movie> dataList;
    private OnItemClickCallback onItemClickCallback;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    Context context;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.dataList = movieArrayList;
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
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {

        holder.tvTitle.setText(dataList.get(position).getTitle());
        holder.tvVoteAverage.setText(dataList.get(position).getVote_average());

        if (dataList.get(position).getPoster_path() == null) {
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.no_image))
                    .fitCenter()
                    .into(holder.imgPoster);
        } else {
            Glide.with(context)
                    .load(urlPoster + dataList.get(position).getPoster_path())
                    .centerCrop()
                    .into(holder.imgPoster);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(dataList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
