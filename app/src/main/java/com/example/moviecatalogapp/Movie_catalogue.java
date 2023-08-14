package com.example.moviecatalogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.moviecatalogapp.model.Movie;
import com.example.moviecatalogapp.model.MovieDescription;
import com.example.moviecatalogapp.moviesView.MovieAdapter;
import com.example.moviecatalogapp.network.APICall;
import com.example.moviecatalogapp.network.Connectivity;
import com.example.moviecatalogapp.storage.SQLSchema;
import com.example.moviecatalogapp.storage.SaveRetrieveDeleteData;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Movie_catalogue extends AppCompatActivity {

    private APICall apiCall;
    private Connectivity connectivity;
    private ArrayList<Movie> movies;
    private ArrayList<Movie> storedMovies;
    private SaveRetrieveDeleteData data;

    // Define data adapter for RecyclerView
    private MovieAdapter movieAdapter;

    // Recycler View component for movies
    private RecyclerView recyclerView;

    // Text view component that displays to the user that the movie searched does not exist
    private TextView no_movie_found_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_catalogue);

        apiCall = new APICall(this); // has the appropriate methods to make and handle API calls
        connectivity = new Connectivity(this); // used to detect whether user's device has connection to the internet

        movies = new ArrayList<>(); // List that holds all Movie instances
        movieAdapter = new MovieAdapter(movies, this::showMovieDetails); // Adapter for Movie recycler view

        storedMovies = new ArrayList<>(); // List that stores Movie instances stored in DB
        data = new SaveRetrieveDeleteData(this);

        // Creates toolbar component
        Toolbar toolbar = findViewById(R.id.movie_cat_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Create and inflate button and text views
        Button refresh_btn = findViewById(R.id.refresh_button);
        refresh_btn.setVisibility(View.INVISIBLE);

        TextView offline_text = findViewById(R.id.offline);
        offline_text.setVisibility(View.INVISIBLE);

        no_movie_found_text = findViewById(R.id.no_movie_found);
        no_movie_found_text.setVisibility(View.INVISIBLE);

        // Creates RecyclerView component and set adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(movieAdapter);

        // Retrieve movies already stored in database (if any)
        for(SQLSchema schema : data.retrieveMovies()) {
            Movie movie = new Movie(schema.poster_path,schema.overview,schema.release_date,schema.movieId,schema.original_title,schema.title,schema.popularity,schema.vote_count,schema.vote_average);
            storedMovies.add(movie);
        }

        // Check if device has internet connection
        if(connectivity.isConnected()) {
            Log.d("App [INFO]", "Device is connected to internet");

            // If there are not Movies already stored in storage, make a API call and fetch movies
            if(storedMovies.size() == 0) {
                Log.d("App [INFO]", "Making API call...");

                apiCall.call("https://app-vpigadas.herokuapp.com/api/movies/", new APICall.MovieCallback() {
                    @Override
                    public void onSuccess(ArrayList<Movie> response_movies) {
                        movies = response_movies;

                        // Update data of recycler view
                        movieAdapter.filterMovies(movies);

                        // Inserting the data to the sql database
                        for (Movie movie : movies) {
                            data.insertMovie(movie.getId(), movie.getOverview(), movie.getPoster_path(),
                                    movie.getRelease_date(), movie.getOriginal_title(), movie.getPopularity(),
                                    movie.getVote_count(), movie.getVote_average(), movie.getTitle());
                        }

                        Log.d("App [INFO] - API Call", "API successful, total movies returned: " + Objects.requireNonNull(recyclerView.getAdapter()).getItemCount());
                    }

                    @Override
                    public void onMovieDetailsSuccess(MovieDescription movieDescription) {}

                    @Override
                    public void onError(VolleyError error) {
                        Log.e("Api-Call", "api error: ", error);
                    }
                }, false);
            }
            else{
                // Update data of recycler view
                movieAdapter.filterMovies(storedMovies);
                Log.d("App [INFO]", "Movie data already present in device storage");
            }
        }
        // Case to handle what happens if device is not connected to the internet
        else if(!connectivity.isConnected()){
            Log.d("App [INFO]", "Device is not connected to internet");
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();

            // If Device has no internet and no data are stored inside storage
            if (data.retrieveMovies().size() == 0) {

                // Display Offline message and refresh button
                refresh_btn.setVisibility(View.VISIBLE);
                offline_text.setVisibility(View.VISIBLE);
            }
            else{
                // Set recycler view movies to the ones stored
                movieAdapter.filterMovies(storedMovies);
            }
        }

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }

    private void showMovieDetails(Movie movie){
        // Navigates user to movie details activity
        Log.d("App [INFO]", "User selected to view details about movie");

        Intent intent = new Intent(Movie_catalogue.this, Movie_description.class);
        int movieId = movie.getId();
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // create toolbar options menu
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Initialize and inflate menu item search bar
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        // We don't want users to filter movies if they are
        // Attach setOnQueryTextListener to check for user input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtering movies recycler view.
                filterView(newText);
                return false;
            }
        });

        return true;
    }

    private void filterView(String text){
        // Creating array to filter data
        ArrayList<Movie> filteredMovies = new ArrayList<>();

        // The available movies in the app (Either through the API call or Device storage)
        ArrayList<Movie> availableMovies;
        if (movies.size()==0) {
            availableMovies = storedMovies;
        }else {
            availableMovies = movies;
        }

        // Match movie title with input text
        for (Movie movie : availableMovies) {
            if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }

        // If user searches for a movie title that does not exist, display message
        // The message will only appear if the device is connected to the internet
        if (filteredMovies.isEmpty() && connectivity.isConnected()) {
            no_movie_found_text.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            no_movie_found_text.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            movieAdapter.filterMovies(filteredMovies);
        }
    }

    // boolean value to know whether user has pressed two times
    boolean userSelectsToExit = false;
    @Override
    public void onBackPressed() {
        // Requires the user to press two time the back button to exit the application

        if (userSelectsToExit) {
            super.onBackPressed();
            data.deleteAllMovies();
            data.deleteAllMoviesDetails();
            finish();
            System.exit(0);
        }

        this.userSelectsToExit = true;
        Toast.makeText(this, "Click again to exit the application", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                userSelectsToExit=false;
            }
        }, 2000);

    }

    //delete all data when the user closes the application

}