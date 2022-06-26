package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.helper.ImgLoader;
import com.example.weatherapp.weatherdata.ForecastWeatherData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {

    private final List<ForecastWeatherData> forecastWeatherDataList;

    public ForecastListAdapter(List<ForecastWeatherData> forecastWeatherDataList) {
        this.forecastWeatherDataList = forecastWeatherDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.forecast_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastWeatherData forecastWeatherData = forecastWeatherDataList.get(holder.getAdapterPosition());
        holder.recycler_date_and_time.setText(forecastWeatherData.getDateAndTime());
        holder.recycler_temperature.setText(""+forecastWeatherData.getTemperature() + "\u2109");
        holder.recycler_weather.setText(forecastWeatherData.getWeather());
        ImgLoader.loadImg(forecastWeatherData, holder.recycler_weather_img);
    }

    @Override
    public int getItemCount() {
        return forecastWeatherDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recycler_date_and_time;
        private final TextView recycler_temperature;
        private final TextView recycler_weather;
        private final ImageView recycler_weather_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recycler_date_and_time = itemView.findViewById(R.id.recycler_date_and_time);
            this.recycler_temperature = itemView.findViewById(R.id.recycler_temperature);
            this.recycler_weather = itemView.findViewById(R.id.recycler_weather);
            this.recycler_weather_img = itemView.findViewById(R.id.recycler_weather_img);
        }
    }
}
