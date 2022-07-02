package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.ViewHolder> {
    private final FavoritesActivity favoritesActivity;
    private final List<String> cityNames;

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

    public void removeCity(int position) {
        cityNames.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return cityNames.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFavoriteCityName;
        private final Button btnRemoveFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvFavoriteCityName = itemView.findViewById(R.id.favorite_city_name);
            this.tvFavoriteCityName.setOnClickListener( view -> {
                favoritesActivity.onFavoriteItemClicked(this.tvFavoriteCityName.getText().toString());
            });
            this.btnRemoveFavorite = itemView.findViewById(R.id.remove_favorite);
            this.btnRemoveFavorite.setOnClickListener( view -> {
                removeCity(getAdapterPosition());
                favoritesActivity.onRemoveButtonClicked(this.tvFavoriteCityName.getText().toString());
            });
        }

    }
}
