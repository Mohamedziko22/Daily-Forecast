package org.mm.dailyforecast.ui.main;

import org.mm.dailyforecast.networks.API;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainRepo {
    MutableLiveData<Object> mSelectWeather;

    public MutableLiveData<Object> selectWeather(String cityName, String apiKey) {
        mSelectWeather = new MutableLiveData<>();
        Observable observable = API.getInstance().selectWeather(cityName, apiKey)
                .subscribeOn(Schedulers.io());
        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                mSelectWeather.postValue(o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mSelectWeather.postValue(e);

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return mSelectWeather;
    }
}
