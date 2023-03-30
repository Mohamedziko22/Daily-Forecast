package org.mm.dailyforecast.models.roomdb;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataWeatherModel {
    @PrimaryKey
    @NonNull
    private UUID uid;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "date")
    private int date;

    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "main")
    private String main;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "icon")
    private String icon;

    public DataWeatherModel(@NonNull UUID uid, String city, int date, int id, String main, String description, String icon) {
        this.uid = uid;
        this.city = city;
        this.date = date;
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    @NonNull
    public UUID getUid() {
        return uid;
    }

    public void setUid(@NonNull UUID uid) {
        this.uid = uid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
