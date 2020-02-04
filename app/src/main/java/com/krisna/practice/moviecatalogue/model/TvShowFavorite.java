package com.krisna.practice.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_show_favorite", indices = {@Index(value = {"tmdbId"}, unique = true)})
public class TvShowFavorite implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tmdbId")
    @NonNull
    private String tmdbId;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "rating")
    @NonNull
    private String rating;

    @ColumnInfo(name = "poster_path")
    @NonNull
    private String poster_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(@NonNull String tmdbId) {
        this.tmdbId = tmdbId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getRating() {
        return rating;
    }

    public void setRating(@NonNull String rating) {
        this.rating = rating;
    }

    @NonNull
    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(@NonNull String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tmdbId);
        dest.writeString(this.title);
        dest.writeString(this.rating);
        dest.writeString(this.poster_path);
    }

    public TvShowFavorite() {
    }

    protected TvShowFavorite(Parcel in) {
        this.id = in.readInt();
        this.tmdbId = in.readString();
        this.title = in.readString();
        this.rating = in.readString();
        this.poster_path = in.readString();
    }

    public static final Parcelable.Creator<TvShowFavorite> CREATOR = new Parcelable.Creator<TvShowFavorite>() {
        @Override
        public TvShowFavorite createFromParcel(Parcel source) {
            return new TvShowFavorite(source);
        }

        @Override
        public TvShowFavorite[] newArray(int size) {
            return new TvShowFavorite[size];
        }
    };
}
