package org.mm.dailyforecast.models.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataWeatherModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
public abstract WeatherDao weatherDao();
}
