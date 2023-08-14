package com.example.moviecatalogapp.moviesView;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogapp.R;
import com.example.moviecatalogapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    // List that holds Movie instances for the RecyclerView
    private List<Movie> movies;

    // The context of the activity that the adapter is created
    private Context context;

    // Item click listener for when the user clicks on a movie
    private final ItemClickListener itemClickListener;

    public MovieAdapter(List<Movie> movies, ItemClickListener itemClickListener)
    {
        this.movies = movies;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movies_recycle_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        // Movie Poster
        String moviePoster = movies.get(position).getPoster_path();
        Glide.with(context).load(moviePoster).into(holder.moviePoster);

        // Movie Title
        String movieTitle = movies.get(position).getTitle();
        // Making movie title appear as bold text
        SpannableString textViewTitle = new SpannableString(movieTitle);
        textViewTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, textViewTitle.length(), 0);
        holder.movieTitle.setText(textViewTitle);

        // Movie Release Date
        String releaseDateYear = "("+ movies.get(position).getRelease_date().split("-")[0] + ")";
        SpannableString textViewDate = new SpannableString(releaseDateYear);
        textViewDate.setSpan(new StyleSpan(Typeface.BOLD), 0, textViewDate.length(), 0);
        holder.movieReleaseDate.setText(textViewDate);

        // Movie Rating
        String rating = movies.get(position).getVote_average() + "/10";
        holder.movieRating.setText(rating);

        // Movie Description
        holder.movieDescription.setText(movies.get(position).getOverview());

        // On click listener. Onclick the movie at the specific position is returned
        holder.itemView.setOnClickListener(view -> this.itemClickListener.onItemClick(movies.get(position)));
    }

    public interface ItemClickListener{
        void onItemClick(Movie movie);
    }

    // Method for filtering the Movies recycler View
    public void filterMovies(ArrayList<Movie> moviesFilterList){
        this.movies = moviesFilterList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
