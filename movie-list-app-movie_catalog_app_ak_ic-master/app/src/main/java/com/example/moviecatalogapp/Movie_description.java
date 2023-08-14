package com.example.moviecatalogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.moviecatalogapp.castView.CastAdapter;
import com.example.moviecatalogapp.model.Movie;
import com.example.moviecatalogapp.model.MovieDescription;
import com.example.moviecatalogapp.network.APICall;
import com.example.moviecatalogapp.network.Connectivity;
import com.example.moviecatalogapp.storage.SQLSchemaDetails;
import com.example.moviecatalogapp.storage.SaveRetrieveDeleteData;

import java.util.ArrayList;
import java.util.Objects;

public class Movie_description extends AppCompatActivity {

    private Connectivity connectivity;
    private APICall apiCall;
    private MovieDescription currentMovie;
    private ArrayList<SQLSchemaDetails> currentMovieDB;
    private SaveRetrieveDeleteData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the default layout to default (Until the API call succeeds)
        setContentView(R.layout.activity_movie_description_default);

        apiCall = new APICall(this);
        connectivity = new Connectivity(this);

        data = new SaveRetrieveDeleteData(this);
        currentMovieDB = new ArrayList<>();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Gets the ID of the movie the user clicked on in the Movie_catalog activity
        int movieId = getIntent().getIntExtra("movieId", 0);
        Log.d("App [INFO]", "Trying to fetch details about movie with ID: " + movieId);

        // Get current movie details from the database
        currentMovieDB = (ArrayList<SQLSchemaDetails>) data.searchByIdDetails(movieId);
        Log.d("APP [INFO]", "Data from database: " + currentMovieDB);

        // If device is connected to the internet, then make API call
        if(connectivity.isConnected()) {
            Log.d("App [INFO]", "Device is connected to internet");
            Log.d("App [INFO]", "Making API call...");

            apiCall.call("https://app-vpigadas.herokuapp.com/api/movies/" + movieId, new APICall.MovieCallback() {

                @Override
                public void onSuccess(ArrayList<Movie> response_movies) {}

                @Override
                public void onMovieDetailsSuccess(MovieDescription movieDescription) {
                    currentMovie = movieDescription;

                    Log.d("App [INFO]", "API successful, movie details returned: : " + currentMovie.toString());

                    // Add movie details in DB
                    data.insertDetails(movieId, currentMovie.getBackdrop_path(), currentMovie.getOverview(), currentMovie.getPoster_path()
                            , currentMovie.getRelease_date(), currentMovie.getOriginal_title(), currentMovie.getVote_average());

                    // Displays (sets values to layout) movie details
                    displayMovieDetails(currentMovie);
                }

                @Override
                public void onError(VolleyError error) {
                    Log.e("Api-Call", "api error: ", error);

                    // Set error layout
                    setContentView(R.layout.activity_movie_description_display_error);

                    // Get button that returns back to main
                    Button backBtn = findViewById(R.id.movie_desc_error_btn);
                    backBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            returnToMainActivity();
                        }
                    });

                }
            }, true);

        }
        // Case to handle what happens if device is not connected to the internet
        else if(!connectivity.isConnected()){
            Log.d("App [INFO]", "Device is not connected to internet");

            // If movie details exist in device storage
            if (currentMovieDB.get(0) != null ){
                Toast.makeText(this, "No internet connection. Some details might be missing", Toast.LENGTH_LONG).show();

                currentMovie = new MovieDescription(currentMovieDB.get(0).original_title,currentMovieDB.get(0).poster_path,
                        currentMovieDB.get(0).backdrop_path,currentMovieDB.get(0).overview,
                        currentMovieDB.get(0).release_date,currentMovieDB.get(0).vote_average, new ArrayList<>());

                displayMovieDetails(currentMovie);
            } else {
                // Else if movie data is not stored

                // Change layout to display message
                setContentView(R.layout.activity_movie_description_no_connection);

                // Get buttons that refresh the activity and return back to main
                Button refreshBtn = findViewById(R.id.movie_desc_refresh_btn);
                Button backBtn = findViewById(R.id.movie_desc_back_btn);

                refreshBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        returnToMainActivity();
                    }
                });
            }
        }

    }

    private void returnToMainActivity(){
        // When called creates new intent from this activity to main activity by creating a new task in stack
        Intent intent = new Intent(Movie_description.this, Movie_catalogue.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // creates toolbar options menu and inflates it with proper xml
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.description_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // gets called whenever user selects an option from the toolbar

        if (item.getItemId() == android.R.id.home) {
            // Returns user to main screen
            returnToMainActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayMovieDetails(MovieDescription movie){
        // Displays movie values to specific views inside the layout

        // Set the correct layout
        setContentView(R.layout.activity_movie_description);

        // Creates and sets toolbar component
        Toolbar toolbar = findViewById(R.id.movie_desc_toolbar);
        setSupportActionBar(toolbar);

        // Back button for toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Creates RecyclerView component for cast
        RecyclerView castRecyclerView = findViewById(R.id.castRecyclerView);

        // Backdrop image
        ImageView movieBackdrop = findViewById(R.id.movieBackDrop);
        Glide.with(getApplicationContext()).load(movie.getBackdrop_path()).into(movieBackdrop);

        // Movie title
        TextView movieTitle = findViewById(R.id.movieDescTitle);
        String movieTitleValue = movie.getOriginal_title();
        // Making movie title appear as bold text
        SpannableString textViewTitle = new SpannableString(movieTitleValue);
        textViewTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, textViewTitle.length(), 0);
        movieTitle.setText(textViewTitle);

        // Movie release date
        TextView movieReleaseDate = findViewById(R.id.movieDescReleaseDate);
        String[] releaseDate = movie.getRelease_date().split("-");
        String formattedReleaseDate = "(" + releaseDate[2] + "/" + releaseDate[1] + "/" + releaseDate[0] + ")";
        SpannableString textViewDate = new SpannableString(formattedReleaseDate);
        textViewDate.setSpan(new StyleSpan(Typeface.BOLD), 0, textViewDate.length(), 0);
        movieReleaseDate.setText(textViewDate);

        // Movie rating
        TextView movieRating = findViewById(R.id.movieDescRating);
        String rating = Math.round(movie.getVote_average() * 10)/10.0 + "/10";
        movieRating.setText(rating);

        // Movie description
        TextView movingDescription = findViewById(R.id.movieDescOverview);
        movingDescription.setText(movie.getOverview());

        // Movie poster image
        ImageView moviePoster = findViewById(R.id.movieDescPoster);
        Glide.with(getApplicationContext()).load(movie.getPoster_path()).into(moviePoster);

        // Bind cast data to recycler view
        castRecyclerView.setAdapter(new CastAdapter(movie.getCast()));
    }
}