package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class ResponseInputPerkembangan {
//    @SerializedName("data")
//    private PesanJadwals data;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
