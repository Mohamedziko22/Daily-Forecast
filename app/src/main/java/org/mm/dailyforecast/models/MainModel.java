package org.mm.dailyforecast.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MainModel implements Serializable {
    @SerializedName("cod")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("cnt")
    private int cnt;
    @SerializedName("list")
    private ArrayList<ListModel> arrayList;

    public MainModel(String code, String message, int cnt, ArrayList<ListModel> arrayList) {
        this.code = code;
        this.message = message;
        this.cnt = cnt;
        this.arrayList = arrayList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<ListModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ListModel> arrayList) {
        this.arrayList = arrayList;
    }
}
