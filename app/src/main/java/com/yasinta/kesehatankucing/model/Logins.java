package com.yasinta.kesehatankucing.model;

import com.google.gson.annotations.SerializedName;

public class Logins {
    @SerializedName("response")
    private String response;

    @SerializedName("token")
    private String token;

    @SerializedName("data")
    private Users users;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
