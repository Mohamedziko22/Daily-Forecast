package org.mm.dailyforecast.networks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.mm.dailyforecast.models.MainModel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String DATABASE_NAME = "weather_room_database";
    public static final String BASE_URL = "https://api.openweathermap.org";

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit retrofit;
    private static API INSTANCE;
    private static ApiInterface anInterface;


    public API() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        anInterface = retrofit.create(ApiInterface.class);

    }

    public static API getInstance() {
        if (INSTANCE == null)
            INSTANCE = new API();
        return INSTANCE;
    }

    public Observable<MainModel> selectWeather(String cityName, String apiKey) {
        return anInterface.selectWeather(cityName, apiKey);
    }
}
