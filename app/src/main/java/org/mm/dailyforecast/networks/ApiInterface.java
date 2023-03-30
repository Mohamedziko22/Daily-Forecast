package org.mm.dailyforecast.networks;

import org.mm.dailyforecast.models.MainModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/data/2.5/forecast")
    Observable<MainModel> selectWeather(@Query("q") String cityName
            , @Query("appid") String apiKey);
}
