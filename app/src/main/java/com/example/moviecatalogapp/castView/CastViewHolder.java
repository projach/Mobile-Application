package com.example.moviecatalogapp.castView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviecatalogapp.R;

public class CastViewHolder extends RecyclerView.ViewHolder {

    TextView castName;

    public CastViewHolder(@NonNull View itemView) {
        super(itemView);

        castName = itemView.findViewById(R.id.castMemberName);
    }
}
