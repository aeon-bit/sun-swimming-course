package com.yasinta.kesehatankucing.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Diagnosas {
    @SerializedName("id")
    private String id;

    @SerializedName("user")
    private Users user;

    @SerializedName("data_jadwal_periksa_id")
    private String data_jadwal_periksa_id;

    @SerializedName("tanggal")
    private String tanggal;

//    @SerializedName("hasil_diagnosa")
//    private String hasil_diagnosa;

//    @SerializedName("saran_pengobatan")
//    private String saran_pengobatan;

    @SerializedName("gejala")
    private ArrayList<Gejalas> gejala;

    @SerializedName("hasil_diagnosa")
    private ArrayList<HasilSarans> hasil_diagnosa;

    @SerializedName("saran_pengobatan")
    private ArrayList<HasilSarans> saran_pengobatan;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getData_jadwal_periksa_id() {
        return data_jadwal_periksa_id;
    }

    public void setData_jadwal_periksa_id(String data_jadwal_periksa_id) {
        this.data_jadwal_periksa_id = data_jadwal_periksa_id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public ArrayList<Gejalas> getGejala() {
        return gejala;
    }

    public void setGejala(ArrayList<Gejalas> gejala) {
        this.gejala = gejala;
    }

    public ArrayList<HasilSarans> getHasil_diagnosa() {
        return hasil_diagnosa;
    }

    public void setHasil_diagnosa(ArrayList<HasilSarans> hasil_diagnosa) {
        this.hasil_diagnosa = hasil_diagnosa;
    }

    public ArrayList<HasilSarans> getSaran_pengobatan() {
        return saran_pengobatan;
    }

    public void setSaran_pengobatan(ArrayList<HasilSarans> saran_pengobatan) {
        this.saran_pengobatan = saran_pengobatan;
    }
}
