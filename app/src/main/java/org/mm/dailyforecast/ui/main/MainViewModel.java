package org.mm.dailyforecast.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Object> mSelectWeather;
    MainRepo repo = new MainRepo();

    public void selectWeather(String cityName, String apiKey) {
        mSelectWeather = new MutableLiveData<>();
        repo.selectWeather(cityName, apiKey)
                .observeForever(new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                mSelectWeather.setValue(o);
            }
        });
    }
}
