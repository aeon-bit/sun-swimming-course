package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class Registers {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Users users;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
