package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponsePembayarans {
//    @SerializedName("data")
//    private ArrayList<Siswas> data;

    @SerializedName("mesage")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
