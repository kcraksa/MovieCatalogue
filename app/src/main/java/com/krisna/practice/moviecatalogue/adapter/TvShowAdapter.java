package com.krisna.practice.moviecatalogue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.krisna.practice.moviecatalogue.R;
import com.krisna.practice.moviecatalogue.model.TvShow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.GridViewHolder> {

    private ArrayList<TvShow> dataList;
    private OnItemClickCallback onItemClickCallback;
    private static final String urlPoster = "https://image.tmdb.org/t/p/w185/";
    Context context;

    public TvShowAdapter(Context context, ArrayList<TvShow> tvShowArrayList) {
        this.context = context;
        this.dataList = tvShowArrayList;
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
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {
        holder.tvTitle.setText(dataList.get(position).getName());
        holder.tvVoteAverage.setText(dataList.get(position).getVote_average());

        Glide.with(context)
                .load(urlPoster + dataList.get(position).getPoster_path())
                .centerCrop()
                .into(holder.imgPoster);

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

            tvTitle = itemView.findViewById(R.id.tv_tv_show_title);
            tvVoteAverage = itemView.findViewById(R.id.tv_tv_show_vote_average);
            imgPoster = itemView.findViewById(R.id.img_tv_show_poster);
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
        void onItemClicked(TvShow data);
    }
}
