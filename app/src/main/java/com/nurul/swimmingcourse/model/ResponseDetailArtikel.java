package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDetailArtikel {
    @SerializedName("data")
    private Artikels data;

    @SerializedName("status")
    private String status;

    public Artikels getData() {
        return data;
    }

    public void setData(Artikels data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
