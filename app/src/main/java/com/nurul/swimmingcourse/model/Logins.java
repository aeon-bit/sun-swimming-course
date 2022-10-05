package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Logins {
    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private Users user;

    @SerializedName("role")
    private String role;

    @SerializedName("errors")
    private ArrayList errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList getErrors() {
        return errors;
    }

    public void setErrors(ArrayList errors) {
        this.errors = errors;
    }
}
