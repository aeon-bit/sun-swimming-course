package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class Registers {

    @SerializedName("message")
    private String message;

    @SerializedName("siswa")
    private Siswas siswa;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Siswas getSiswa() {
        return siswa;
    }

    public void setSiswa(Siswas siswa) {
        this.siswa = siswa;
    }

    //    @SerializedName("nama")
//    private String nama;
//
//    @SerializedName("tempat_lahir")
//    private String tempat_lahir;
//
//    @SerializedName("tanggal_lahir")
//    private String tanggal_lahir;
//
//    @SerializedName("jenis_kelamin")
//    private String jenis_kelamin;
//
//    @SerializedName("nama_ortu")
//    private String nama_ortu;
//
//    @SerializedName("alamat")
//    private String alamat;
//
//    @SerializedName("no_telp")
//    private String no_telp;
//
//    @SerializedName("username")
//    private String username;
//
//    @SerializedName("password")
//    private String password;


}
