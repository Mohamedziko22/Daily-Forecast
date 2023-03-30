package org.mm.dailyforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class ListModel implements Serializable {
    @SerializedName("dt")
    private int date;

    @SerializedName("weather")
    private ArrayList<WeatherModel> listWeather;

    public ListModel(int date, ArrayList<WeatherModel> listWeather) {
        this.date = date;
        this.listWeather = listWeather;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public ArrayList<WeatherModel> getListWeather() {
        return listWeather;
    }

    public void setListWeather(ArrayList<WeatherModel> listWeather) {
        this.listWeather = listWeather;
    }
}

