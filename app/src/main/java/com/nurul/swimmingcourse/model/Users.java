package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class Users {

//    @SerializedName("response")
//    private String Response;

    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("nama_pemilik")
    private String nama_pemilik;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("no_hp")
    private String no_hp;

    @SerializedName("username")
    private String username;

    @SerializedName("nama_kucing")
    private String nama_kucing;

    @SerializedName("jenis_kucing")
    private String jenis_kucing;


    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama_kucing() {
        return nama_kucing;
    }

    public void setNama_kucing(String nama_kucing) {
        this.nama_kucing = nama_kucing;
    }

    public String getJenis_kucing() {
        return jenis_kucing;
    }

    public void setJenis_kucing(String jenis_kucing) {
        this.jenis_kucing = jenis_kucing;
    }
}
