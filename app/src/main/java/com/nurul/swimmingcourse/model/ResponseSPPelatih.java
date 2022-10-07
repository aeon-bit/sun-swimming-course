package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSPPelatih {
    @SerializedName("data")
    private ArrayList<Pelatihs> data;

    public ArrayList<Pelatihs> getData() {
        return data;
    }

    public void setData(ArrayList<Pelatihs> data) {
        this.data = data;
    }
}
