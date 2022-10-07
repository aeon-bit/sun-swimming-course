package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class Swimmings {

//    @SerializedName("response")
//    private String Response;

    @SerializedName("id")
    private String id;

    @SerializedName("token")
    private String token;

    @SerializedName("judul_info")
    private String judul_info;

    @SerializedName("detail_info")
    private String detail_info;

    @SerializedName("foto")
    private String foto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJudul_info() {
        return judul_info;
    }

    public void setJudul_info(String judul_info) {
        this.judul_info = judul_info;
    }

    public String getDetail_info() {
        return detail_info;
    }

    public void setDetail_info(String detail_info) {
        this.detail_info = detail_info;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
