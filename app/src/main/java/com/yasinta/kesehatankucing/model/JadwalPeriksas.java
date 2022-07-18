package com.yasinta.kesehatankucing.model;

import com.google.gson.annotations.SerializedName;

public class JadwalPeriksas {
    @SerializedName("id")
    private String id;

    @SerializedName("tanggal_periksa")
    private String tanggal_periksa;

    @SerializedName("status_periksa")
    private String status_periksa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal_periksa() {
        return tanggal_periksa;
    }

    public void setTanggal_periksa(String tanggal_periksa) {
        this.tanggal_periksa = tanggal_periksa;
    }

    public String getStatus_periksa() {
        return status_periksa;
    }

    public void setStatus_periksa(String status_periksa) {
        this.status_periksa = status_periksa;
    }
}
