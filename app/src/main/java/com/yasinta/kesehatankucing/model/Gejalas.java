package com.yasinta.kesehatankucing.model;

import com.google.gson.annotations.SerializedName;

public class Gejalas {
    @SerializedName("id")
    private String id;

    @SerializedName("kode_gejala")
    private String kode_gejala;

    @SerializedName("nama_gejala")
    private String nama_gejala;

    @SerializedName("gejala")
    private String gejala;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }

    public String getNama_gejala() {
        return nama_gejala;
    }

    public void setNama_gejala(String nama_gejala) {
        this.nama_gejala = nama_gejala;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }
}
