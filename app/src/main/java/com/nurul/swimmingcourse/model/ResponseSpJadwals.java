package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSpJadwals {
    @SerializedName("data")
    private ArrayList<JadwalPeriksas> data;

    public ArrayList<JadwalPeriksas> getData() {
        return data;
    }

    public void setData(ArrayList<JadwalPeriksas> data) {
        this.data = data;
    }

}
