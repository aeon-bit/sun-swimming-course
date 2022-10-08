package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class ResponseTesKesehatan {
    @SerializedName("data")
    private PesanJadwals data;

    @SerializedName("status")
    private String status;


    public PesanJadwals getData() {
        return data;
    }

    public void setData(PesanJadwals data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
