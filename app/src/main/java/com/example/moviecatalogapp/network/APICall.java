/**
* Used to make and handle HTTP cals.
* This class has the appropriate methods to make a HTTP request and handle it using callback functions
* */

package com.example.moviecatalogapp.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecatalogapp.json.JsonResponse;
import com.example.moviecatalogapp.model.Movie;
import com.example.moviecatalogapp.model.MovieDescription;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class APICall {
    private final RequestQueue queue;
    private ArrayList<Movie> movies;
    private MovieDescription movieDescription;

    public APICall(Context context){
        this.queue = Volley.newRequestQueue(context);
        this.movies = new ArrayList<>();
    }

    public interface MovieCallback {
        void onSuccess(ArrayList<Movie> movies);
        void onMovieDetailsSuccess(MovieDescription movieDescription);
        void onError(VolleyError error);
    }

    public void call(String url, MovieCallback callback, Boolean getMovieDetails){
        /*
         * String url: The url of the endpoint we want to make a request to
         * MovieCallBack callback: An instance of the Interface for the callback functions
         * Boolean getMovieDetails: If TRUE then it means that the endpoint regards a specific movie details. Else, the endpoint is to fetch data for all movies
        */
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {

                            if (!getMovieDetails) {
                                JsonResponse element = new Gson().fromJson(response, JsonResponse.class);
                                // Extract movies
                                for (JsonElement jsonElement : element.getMessage()) {
                                    movies.add(new Gson().fromJson(jsonElement, Movie.class));
                                }
                                // After all the movies have been added to the list, make callback call
                                callback.onSuccess(movies);
                            }else{
                                // Extract movie description
                                movieDescription = new Gson().fromJson(response, MovieDescription.class);
                                // Make Callback call with movie details
                                callback.onMovieDetailsSuccess(movieDescription);
                            }
                        }
                        else{
                            // change later to something or delete
                            callback.onSuccess(new ArrayList<>());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                        callback.onError(error);
                    }
                }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

        queue.add(request);
    }

}
