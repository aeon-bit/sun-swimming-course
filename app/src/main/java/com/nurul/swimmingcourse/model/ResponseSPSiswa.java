package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSPSiswa {
    @SerializedName("data")
    private ArrayList<Siswas> data;

    public ArrayList<Siswas> getData() {
        return data;
    }

    public void setData(ArrayList<Siswas> data) {
        this.data = data;
    }
}
