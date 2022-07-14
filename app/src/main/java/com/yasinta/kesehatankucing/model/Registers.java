package com.yasinta.kesehatankucing.model;

import com.google.gson.annotations.SerializedName;

public class Registers {

    @SerializedName("response")
    private String response;

    @SerializedName("data")
    private Users users;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
