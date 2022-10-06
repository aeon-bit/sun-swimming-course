package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class Users {

//    @SerializedName("response")
//    private String Response;

    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("tempat_lahir")
    private String tempat_lahir;

    @SerializedName("tanggal_lahir")
    private String tanggal_lahir;

    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;

    @SerializedName("nama_ortu")
    private String nama_ortu;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    //pelatih
    @SerializedName("foto")
    private String foto;

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

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNama_ortu() {
        return nama_ortu;
    }

    public void setNama_ortu(String nama_ortu) {
        this.nama_ortu = nama_ortu;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
