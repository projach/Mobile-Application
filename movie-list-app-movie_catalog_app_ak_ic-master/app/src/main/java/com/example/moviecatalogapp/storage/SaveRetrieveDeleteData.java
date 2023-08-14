package com.example.moviecatalogapp.storage;

import android.content.Context;

import com.example.moviecatalogapp.model.MovieDescription;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;


public class SaveRetrieveDeleteData {
    private Context context;
    private SQLSchema SQLSchema;
    private SQLSchemaDetails SQLSchemaDetails;
    private Dao dao;
    private DaoDetails daoDetails;

    public SaveRetrieveDeleteData(Context context) {
        this.context = context;
        this.SQLSchema = new SQLSchema();
        this.SQLSchemaDetails = new SQLSchemaDetails();
        this.dao = MovieDatabase.getDatabase(context).movieDao();
        this.daoDetails = MovieDatabase.getDatabase(context).movieDaoDetails();

    }

    //change cast from jsonArray to cast model
    public void insertDetails(int id, String backdrop_path, String overview, String posterPath, String releaseDate, String originalTitle, double voteAvg){
        SQLSchemaDetails.movieId = id;
        SQLSchemaDetails.backdrop_path = backdrop_path;
        SQLSchemaDetails.overview = overview;
        SQLSchemaDetails.poster_path = posterPath;
        SQLSchemaDetails.release_date = releaseDate;
        SQLSchemaDetails.original_title = originalTitle;
        SQLSchemaDetails.vote_average = voteAvg;
        daoDetails.insertDetails(SQLSchemaDetails);
    }

    public List<SQLSchemaDetails> retrieveMoviesDetails(){
        return daoDetails.getAllMoviesDetails();
    }

    public void deleteAllMoviesDetails() {
        daoDetails.deleteAllMoviesDetails();
    }

    public void insertMovie(int id, String overview, String posterPath, String releaseDate, String originalTitle, double popularity, int voteCount, double voteAvg, String title){
        SQLSchema.movieId = id;
        SQLSchema.title = title;
        SQLSchema.overview = overview;
        SQLSchema.poster_path = posterPath;
        SQLSchema.release_date = releaseDate;
        SQLSchema.original_title = originalTitle;
        SQLSchema.popularity = popularity;
        SQLSchema.vote_count = voteCount;
        SQLSchema.vote_average = voteAvg;
        dao.insert(SQLSchema);
    }

    public List<SQLSchema> retrieveMovies(){
        return dao.getAllMovies();
    }

    public List<SQLSchema> searchByName(String searchMovie){ return dao.searchByName(searchMovie);}

    public void deleteAllMovies() {
        dao.deleteAllMovies();
    }

    public List<SQLSchemaDetails> searchByIdDetails(int searchMovie){ return daoDetails.searchByIdDetails(searchMovie);}
}
