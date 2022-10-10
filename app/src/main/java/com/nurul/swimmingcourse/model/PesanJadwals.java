package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PesanJadwals {
    @SerializedName("id")
    private String id;

    @SerializedName("pelatih_id")
    private String pelatih_id;

    @SerializedName("siswa_id")
    private String siswa_id;

    @SerializedName("hari")
    private String hari;

    @SerializedName("jam")
    private String jam;

    @SerializedName("lokasi")
    private String lokasi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPelatih_id() {
        return pelatih_id;
    }

    public void setPelatih_id(String pelatih_id) {
        this.pelatih_id = pelatih_id;
    }

    public String getSiswa_id() {
        return siswa_id;
    }

    public void setSiswa_id(String siswa_id) {
        this.siswa_id = siswa_id;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
