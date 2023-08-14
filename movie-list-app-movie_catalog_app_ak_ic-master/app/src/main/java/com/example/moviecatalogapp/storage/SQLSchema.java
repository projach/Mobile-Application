package com.example.moviecatalogapp.storage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_catalogue")
public class SQLSchema {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "movieId")
    public int movieId;
    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "overview")
    public String overview;

    @ColumnInfo(name = "poster_path")
    public String poster_path;

    @ColumnInfo(name = "release_date")
    public String release_date;

    @ColumnInfo(name = "original_title")
    public String original_title;

    @ColumnInfo(name = "popularity")
    public double popularity;

    @ColumnInfo(name = "vote_count")
    public int vote_count;

    @ColumnInfo(name = "vote_average")
    public double vote_average;

    //created toString because the toString returned the schema and not the properties of schema.
    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + poster_path + '\'' +
                ", releaseDate='" + release_date + '\'' +
                ", originalTitle='" + original_title + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + vote_count +
                ", voteAvg=" + vote_average +
                '}';
    }
}
