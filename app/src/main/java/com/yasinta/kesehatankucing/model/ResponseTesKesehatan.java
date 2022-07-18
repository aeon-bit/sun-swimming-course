package com.yasinta.kesehatankucing.model;

import com.google.gson.annotations.SerializedName;

public class ResponseTesKesehatan {
    @SerializedName("data")
    private Diagnosas data;

    @SerializedName("status")
    private String status;


    public Diagnosas getData() {
        return data;
    }

    public void setData(Diagnosas data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
