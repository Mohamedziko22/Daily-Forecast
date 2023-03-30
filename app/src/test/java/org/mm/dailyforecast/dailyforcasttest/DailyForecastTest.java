package org.mm.dailyforecast.dailyforcasttest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mm.dailyforecast.models.FUtilsValidation;
import org.mm.dailyforecast.models.MainModel;
import org.mm.dailyforecast.networks.API;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class DailyForecastTest {
    @Test
    public void whenInputValidCityName() {
        String cityName = "cairo";
        boolean result = !FUtilsValidation.isEmpty(cityName);
        assertEquals(true, result);
    }

    @Test
    public void whenCallApiWithCityNameCorrectly() {
        Observable observable = API.getInstance().selectWeather("cairo", "44e68b0a5acaced3f2d8c6943896acba")
                .subscribeOn(Schedulers.io());
        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                assertNotNull(o);
                assert (o instanceof MainModel);
                assertEquals("200", ((MainModel) o).getCode());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }

    @Test
    public void whenCallApiWithCityInvalidName() {
        Observable observable = API.getInstance().selectWeather("122sss", "44e68b0a5acaced3f2d8c6943896acba")
                .subscribeOn(Schedulers.io());
        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                assert (o instanceof MainModel);
                assertNotEquals("200", ((MainModel) o).getCode());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }

    @Test
    public void whenCallApiWithThrowError() {
        Observable observable = API.getInstance().selectWeather(null, "44e68b0a5acaced3f2d8c6943896acba")
                .subscribeOn(Schedulers.io());
        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                assert (o instanceof Throwable);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                assert (true);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }

}
