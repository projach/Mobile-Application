/**
 * Describes a Movie object.
 */

package com.example.moviecatalogapp.model;

import androidx.annotation.NonNull;

public class Movie {
    private String poster_path;
    private String overview;
    private String release_date;
    private int id;
    private String original_title;
    private String title;
    private double popularity;
    private int vote_count;
    private double vote_average;

    public Movie(String poster_path, String overview, String release_date, int id, String original_title, String title, double popularity, int vote_count, double vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.original_title = original_title;
        this.title = title;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", posterPath='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + release_date + '\'' +
                ", originalTitle='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + vote_count +
                ", voteAvg=" + vote_average +
                '}';
    }
}
