package org.mm.dailyforecast.models.roomdb;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WeatherDao {
    @Insert
    void insertAll(DataWeatherModel... DataWeatherModel);

    @Query("DELETE FROM DataWeatherModel WHERE city LIKE :city")
    void delete(String city);

    @Query("SELECT * FROM DataWeatherModel WHERE city LIKE :city")
    List<DataWeatherModel> list(String city);

}
