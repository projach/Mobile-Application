package com.example.moviecatalogapp.storage;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@androidx.room.Dao
public interface Dao {
    // insert into the database
    @Insert
    void insert(SQLSchema SQLSchema);

    //take all the catalogue data from the database
    @Query("SELECT * FROM movie_catalogue")
    List<SQLSchema> getAllMovies();

    //deletes all movies
    @Query("DELETE FROM movie_catalogue")
    void deleteAllMovies();

    //returns all the movies that have the String searchMovie inside them and it is not case sensitive using the COLLATE NOCASE
    @Query("SELECT * FROM movie_catalogue WHERE original_title LIKE '%' || :searchMovie || '%' COLLATE NOCASE")
    List<SQLSchema> searchByName(String searchMovie);
}
