package com.example.moviefavorite.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_favorite", indices = {@Index(value = {"tmdbId"}, unique = true)})
public class MovieFavorite implements Parcelable {
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

    public MovieFavorite(int id, @NonNull String tmdbId, @NonNull String title, @NonNull String rating, @NonNull String poster_path) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.title = title;
        this.rating = rating;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
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

    protected MovieFavorite(Parcel in) {
        this.id = in.readInt();
        this.tmdbId = in.readString();
        this.title = in.readString();
        this.rating = in.readString();
        this.poster_path = in.readString();
    }

    public static final Creator<MovieFavorite> CREATOR = new Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };
}
