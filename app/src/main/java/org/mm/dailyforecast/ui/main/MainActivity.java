package org.mm.dailyforecast.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.mm.dailyforecast.R;
import org.mm.dailyforecast.adapters.WeatherAdapter;
import org.mm.dailyforecast.models.roomdb.AppDatabase;
import org.mm.dailyforecast.models.roomdb.DataWeatherModel;
import org.mm.dailyforecast.models.FUtilsValidation;
import org.mm.dailyforecast.models.ListModel;
import org.mm.dailyforecast.models.MainModel;
import org.mm.dailyforecast.models.NetworkAvailable;
import org.mm.dailyforecast.models.roomdb.WeatherDao;
import org.mm.dailyforecast.networks.API;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.txt_message_error)
    TextView txtMessage;
    @BindView(R.id.txt_warning)
    TextView txtWarning;
    @BindView(R.id.edt_city_name)
    EditText edtCityName;
    @BindView(R.id.recycler_weather)
    RecyclerView recycler;
    @BindView(R.id.bar)
    ProgressBar bar;

    NetworkAvailable networkAvailable;
    MainViewModel mainViewModel;
    ArrayList<DataWeatherModel> arrayList = new ArrayList<>();
    WeatherAdapter weatherAdapter;
    AppDatabase appDatabase;
    WeatherDao weatherDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupComponent();
    }

    private void setupComponent() {
        networkAvailable = new NetworkAvailable(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, API.DATABASE_NAME).allowMainThreadQueries().build();
        weatherDao = appDatabase.weatherDao();
        btnSearch.setOnClickListener(this);
        btnRetry.setOnClickListener(this);
        //build recycler
        weatherAdapter = new WeatherAdapter(arrayList, getBaseContext());
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
        recycler.setAdapter(weatherAdapter);

        edtTextWatcher();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
            case R.id.btn_retry:
                selectCityDetails();
                break;

        }
    }

    private void edtTextWatcher() {
        edtCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    txtMessage.setText(getString(R.string.enter_city_name));
                    txtMessage.setVisibility(View.GONE);
                } else {
                    txtMessage.setText(getString(R.string.enter_city_name));
                    txtMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 || arrayList.size() > 0) {
                    txtMessage.setText(getString(R.string.enter_city_name));
                    txtMessage.setVisibility(View.GONE);
                } else {
                    txtMessage.setText(getString(R.string.enter_city_name));
                    txtMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void selectCityDetails() {
        if (!FUtilsValidation.isEmpty(edtCityName, getString(R.string.required))) {
            if (networkAvailable.isNetworkAvailable()) {
                bar.setVisibility(View.VISIBLE);
                //call api
                mainViewModel.selectWeather(edtCityName.getText().toString().trim()
                        , getString(R.string.api_key));
                mainViewModel.mSelectWeather.observe(this, new Observer<Object>() {
                    @Override
                    public void onChanged(Object o) {
                        bar.setVisibility(View.GONE);
                        if (o instanceof Throwable) {
                            //set error here and retry if not data in cash
                            checkCashData(getString(R.string.sorry_error)
                                    , edtCityName.getText().toString().trim().toLowerCase());
                        } else if (o != null) {
                            MainModel mainModel = (MainModel) o;
                            if (mainModel.getCode().equals("200")) {
                                recycler.setVisibility(View.VISIBLE);
                                txtWarning.setVisibility(View.GONE);
                                txtMessage.setVisibility(View.GONE);
                                btnRetry.setVisibility(View.GONE);
                                arrayList = addRecyclerList(mainModel.getArrayList()
                                        , edtCityName.getText().toString().trim().toLowerCase());
                                weatherAdapter.setList(arrayList);
                                weatherAdapter.notifyDataSetChanged();
                                modifyRoomDatabase(arrayList, edtCityName.getText().toString().trim().toLowerCase());

                            } else {
                                txtWarning.setVisibility(View.GONE);
                                recycler.setVisibility(View.GONE);
                                txtMessage.setVisibility(View.VISIBLE);
                                btnRetry.setVisibility(View.VISIBLE);
                                txtMessage.setText(getString(R.string.sorry_error));
                            }
                        }
                    }
                });
            } else {
                //set error connection and retry if not data in cash
                checkCashData(getString(R.string.error_connection)
                        , edtCityName.getText().toString().trim().toLowerCase());
            }
        }
    }

    private ArrayList<DataWeatherModel> addRecyclerList(ArrayList<ListModel> arrayList, String cityName) {
        ArrayList<DataWeatherModel> recyclerList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            recyclerList.add(new DataWeatherModel(UUID.randomUUID(), cityName, arrayList.get(i).getDate()
                    , arrayList.get(i).getListWeather().get(0).getId()
                    , arrayList.get(i).getListWeather().get(0).getMain()
                    , arrayList.get(i).getListWeather().get(0).getDescription()
                    , arrayList.get(i).getListWeather().get(0).getIcon()));
        }
        return recyclerList;
    }

    private void modifyRoomDatabase(ArrayList<DataWeatherModel> listDataCity, String cityName) {
        if (!weatherDao.list(cityName).isEmpty()) weatherDao.delete(cityName);
        for (int i = 0; i < listDataCity.size(); i++) {
            weatherDao.insertAll(new DataWeatherModel(listDataCity.get(i).getUid()
                    , listDataCity.get(i).getCity(), listDataCity.get(i).getDate(), listDataCity.get(i).getId()
                    , listDataCity.get(i).getMain(), listDataCity.get(i).getDescription()
                    , listDataCity.get(i).getIcon()));
        }
    }

    private ArrayList<DataWeatherModel> getListFromRoom(String cityName) {
        ArrayList<DataWeatherModel> recyclerList = (ArrayList<DataWeatherModel>) weatherDao.list(cityName);
        return recyclerList;
    }

    private void checkCashData(String message, String cityName) {
        arrayList = getListFromRoom(cityName);
        if (arrayList.size() > 0) {
            recycler.setVisibility(View.VISIBLE);
            txtWarning.setVisibility(View.VISIBLE);
            txtMessage.setVisibility(View.GONE);
            btnRetry.setVisibility(View.GONE);
            weatherAdapter.setList(arrayList);
            weatherAdapter.notifyDataSetChanged();
        } else {
            txtWarning.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
            txtMessage.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            txtMessage.setText(message);
        }
    }
}