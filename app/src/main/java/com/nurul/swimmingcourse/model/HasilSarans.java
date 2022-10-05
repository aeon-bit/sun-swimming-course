package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class HasilSarans {
    @SerializedName("hasil_diagnosa")
    private String hasil_diagnosa;

    @SerializedName("saran_pengobatan")
    private String saran_pengobatan;

    public String getHasil_diagnosa() {
        return hasil_diagnosa;
    }

    public void setHasil_diagnosa(String hasil_diagnosa) {
        this.hasil_diagnosa = hasil_diagnosa;
    }

    public String getSaran_pengobatan() {
        return saran_pengobatan;
    }

    public void setSaran_pengobatan(String saran_pengobatan) {
        this.saran_pengobatan = saran_pengobatan;
    }
}
