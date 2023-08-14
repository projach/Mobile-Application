package com.example.moviecatalogapp.moviesView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView moviePoster;
    TextView movieTitle;
    TextView movieReleaseDate;
    TextView movieRating;
    TextView movieDescription;


    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        moviePoster = itemView.findViewById(R.id.moviePoster);
        movieTitle = itemView.findViewById(R.id.movieTitle);
        movieReleaseDate = itemView.findViewById(R.id.movieReleaseDate);
        movieRating = itemView.findViewById(R.id.movieRating);
        movieDescription = itemView.findViewById(R.id.movieDescription);

    }
}
