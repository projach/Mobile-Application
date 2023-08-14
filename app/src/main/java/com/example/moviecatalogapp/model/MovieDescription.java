/**
 * Describes the description for a specific movie. Has a nested Cast class for the movie cast.
 */

package com.example.moviecatalogapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MovieDescription {

    private String original_title;

    private String poster_path;

    private String backdrop_path;

    private String overview;

    private String release_date;

    private Double vote_average;

    private ArrayList<Cast> cast;

    public MovieDescription(String original_title, String poster_path, String backdrop_path, String overview, String release_date, Double vote_average, ArrayList<Cast> cast) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.cast = cast;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public class Cast {
        private int gender;

        private int id;

        private String name;

        private String character;

        private int order;

        public Cast(int gender, int id, String name, String character, int order) {
            this.gender = gender;
            this.id = id;
            this.name = name;
            this.character = character;
            this.order = order;
        }

        public String getName() {
            return name;
        }

        @NonNull
        @Override
        public String toString() {
            return "Cast{" +
                    "gender=" + gender +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", character='" + character + '\'' +
                    ", order=" + order +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieDescription{" +
                "original_title='" + original_title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", cast=" + cast +
                '}';
    }
}
