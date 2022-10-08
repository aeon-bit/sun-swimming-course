package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseInfoPerkembangan {
    @SerializedName("data")
    private ArrayList<Perkembangans> data;

    public ArrayList<Perkembangans> getData() {
        return data;
    }

    public void setData(ArrayList<Perkembangans> data) {
        this.data = data;
    }
}
