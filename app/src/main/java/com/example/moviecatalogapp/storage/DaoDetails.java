package com.example.moviecatalogapp.storage;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@androidx.room.Dao
public interface DaoDetails {
    @Insert
    void insertDetails(SQLSchemaDetails SQLSchemaDetails);

    //take all the details data from the database
    @Query("SELECT * FROM movie_details")
    List<SQLSchemaDetails> getAllMoviesDetails();

    //deletes all movies
    @Query("DELETE FROM movie_details")
    void deleteAllMoviesDetails();

    @Query("SELECT * FROM movie_catalogue WHERE movieId LIKE '%' || :searchMovie || '%'")
    List<SQLSchemaDetails> searchByIdDetails(int searchMovie);
}
