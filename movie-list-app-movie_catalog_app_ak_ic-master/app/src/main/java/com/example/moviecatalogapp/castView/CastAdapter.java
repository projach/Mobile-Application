package com.example.moviecatalogapp.castView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogapp.R;
import com.example.moviecatalogapp.model.MovieDescription;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    // List that hold Cast instances for Recycler View
    private List<MovieDescription.Cast> castList;

    public CastAdapter(List<MovieDescription.Cast> castList){
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_recycler_view, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        // Cast member name
        String castName = castList.get(position).getName();
        holder.castName.setText(castName);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
