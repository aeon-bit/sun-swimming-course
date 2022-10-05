package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class ResponseBookingJadwals {
    @SerializedName("data")
    private JadwalPeriksas data;

    @SerializedName("status")
    private String status;


    public JadwalPeriksas getData() {
        return data;
    }

    public void setData(JadwalPeriksas data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
