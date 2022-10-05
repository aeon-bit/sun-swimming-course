package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Diagnosas {
    @SerializedName("id")
    private String id;

    @SerializedName("nama_kucing")
    private String nama_kucing;

    @SerializedName("jenis_kucing")
    private String jenis_kucing;

    @SerializedName("nama_pemilik")
    private String nama_pemilik;

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

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
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
