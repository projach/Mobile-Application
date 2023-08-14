package com.example.moviecatalogapp.storage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviecatalogapp.model.MovieDescription;
import com.google.gson.JsonArray;

import java.util.ArrayList;

@Entity(tableName = "movie_details")
public class SQLSchemaDetails {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "movieId")
    public int movieId;
    @ColumnInfo(name = "overview")
    public String overview;

    @ColumnInfo(name = "poster_path")
    public String poster_path;

    @ColumnInfo(name = "release_date")
    public String release_date;

    @ColumnInfo(name = "original_title")
    public String original_title;

    @ColumnInfo(name = "vote_average")
    public double vote_average;

    @ColumnInfo(name = "backdrop_path")
    public String backdrop_path;

    @NonNull
    @Override
    public String toString() {
        return "SQLSchemaDetails{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", overview=" + overview +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", original_title='" + original_title + '\'' +
                ", vote_average=" + vote_average +
                ", backdrop_path='" + backdrop_path + '\'' +
                '}';
    }
}
