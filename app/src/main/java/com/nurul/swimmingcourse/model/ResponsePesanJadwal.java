package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class ResponsePesanJadwal {
    @SerializedName("data")
    private PesanJadwals data;

    @SerializedName("message")
    private String message;

    public PesanJadwals getData() {
        return data;
    }

    public void setData(PesanJadwals data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
