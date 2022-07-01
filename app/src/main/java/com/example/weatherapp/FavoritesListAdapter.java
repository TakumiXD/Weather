package com.example.weatherapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {
    private FavoritesActivity favoritesActivity;
    private List<String> cityNames;

    public FavoritesListAdapter(FavoritesActivity favoritesActivity, List<String> cityNames) {
        this.favoritesActivity = favoritesActivity;
        this.cityNames = cityNames;
        this.setHasStableIds(true);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvFavoriteCityName.setText(cityNames.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return cityNames.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvFavoriteCityName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvFavoriteCityName = itemView.findViewById(R.id.favorite_city_name);
        }

        @Override
        public void onClick(View view) {
            favoritesActivity.onFavoriteItemClick(this.tvFavoriteCityName.getText().toString());
        }
    }
}
