package org.mm.dailyforecast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mm.dailyforecast.R;
import org.mm.dailyforecast.models.roomdb.DataWeatherModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.Holder> {
    ArrayList<DataWeatherModel> arrayList;
    Context mContext;

    public WeatherAdapter(ArrayList<DataWeatherModel> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(arrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size() != 0 ? arrayList.size() : 0;
    }


    public void setList(ArrayList<DataWeatherModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtDate, txtMain, txtDesc;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtMain = itemView.findViewById(R.id.txt_main);
            txtDesc = itemView.findViewById(R.id.txt_desc);
        }

        public void bind(final DataWeatherModel item, int position) {
            String input = String.valueOf(item.getDate());
            Date date = new Date(Long.parseLong(input) * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            txtDate.setText(dateFormat.format(date));
            txtMain.setText(mContext.getString(R.string.main_is) + item.getMain());
            txtDesc.setText(item.getDescription());

        }
    }
}
