package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.helper.ImgLoader;
import com.example.weatherapp.weatherdata.ForecastWeatherData;

import java.util.ArrayList;
import java.util.List;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {

    private final List<ForecastWeatherData> forecastWeatherDataList = new ArrayList<>();
    private static final String FAHRENHEIT_SYMBOL = "\u00B0";
    private final boolean USE_GPS;
    private static final String LOG_NOTIFY_DATA_SET_CHANGED_TAG = "notifyDataSetChanged";

    public ForecastListAdapter(boolean USE_GPS) {
        this.setHasStableIds(true);
        this.USE_GPS = USE_GPS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.forecast_data_item, parent, false);
        Log.d(LOG_NOTIFY_DATA_SET_CHANGED_TAG, "onCreateViewHolder called");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastWeatherData forecastWeatherData = forecastWeatherDataList.get(holder.getAdapterPosition());
        holder.tvDateAndTime.setText(forecastWeatherData.getDateAndTime());
        holder.tvTemperature.setText(""+forecastWeatherData.getTemperature() + FAHRENHEIT_SYMBOL);
        holder.tvWeather.setText(forecastWeatherData.getWeather());
        if (USE_GPS) {
            ImgLoader.loadImg(forecastWeatherData, holder.ivWeatherImg);
        }
        Log.d(LOG_NOTIFY_DATA_SET_CHANGED_TAG, "onBindViewHolder called with position " + position);
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
        Log.d(LOG_NOTIFY_DATA_SET_CHANGED_TAG, "getItemViewType called with position " + position);
        return position;
    }

    @VisibleForTesting
    public List<ForecastWeatherData> getForecastWeatherDataList() {
        return forecastWeatherDataList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setForecastWeatherDataList(List<ForecastWeatherData> forecastWeatherDataList) {
        this.forecastWeatherDataList.clear();
        this.forecastWeatherDataList.addAll(forecastWeatherDataList);
        notifyDataSetChanged();
        Log.d(LOG_NOTIFY_DATA_SET_CHANGED_TAG, "notifyDataSetChanged called");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateAndTime;
        private final TextView tvTemperature;
        private final TextView tvWeather;
        private final ImageView ivWeatherImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDateAndTime = itemView.findViewById(R.id.recycler_date_and_time);
            this.tvTemperature = itemView.findViewById(R.id.recycler_temperature);
            this.tvWeather = itemView.findViewById(R.id.recycler_weather);
            this.ivWeatherImg = itemView.findViewById(R.id.recycler_weather_img);
        }
    }
}
