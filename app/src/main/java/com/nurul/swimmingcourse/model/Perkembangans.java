package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

public class Perkembangans {

//    @SerializedName("response")
//    private String Response;

    @SerializedName("id")
    private String id;

    @SerializedName("siswa")
    private String siswa;

    @SerializedName("pelatih")
    private String pelatih;

    @SerializedName("tanggal_latihan")
    private String tanggal_latihan;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("keterangan")
    private String keterangan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiswa() {
        return siswa;
    }

    public void setSiswa(String siswa) {
        this.siswa = siswa;
    }

    public String getPelatih() {
        return pelatih;
    }

    public void setPelatih(String pelatih) {
        this.pelatih = pelatih;
    }

    public String getTanggal_latihan() {
        return tanggal_latihan;
    }

    public void setTanggal_latihan(String tanggal_latihan) {
        this.tanggal_latihan = tanggal_latihan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
