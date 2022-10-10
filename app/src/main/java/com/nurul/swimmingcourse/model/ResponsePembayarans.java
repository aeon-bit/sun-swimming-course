package com.nurul.swimmingcourse.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponsePembayarans {
//    @SerializedName("data")
//    private ArrayList<Siswas> data;

    @SerializedName("siswa_id")
    private String siswa_id;

    @SerializedName("tanggal_bayar")
    private String tanggal_bayar;

    @SerializedName("jumlah_bayar")
    private String jumlah_bayar;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("bukti_pembayaran")
    private String bukti_pembayaran;

    @SerializedName("id")
    private String id;

    public String getSiswa_id() {
        return siswa_id;
    }

    public void setSiswa_id(String siswa_id) {
        this.siswa_id = siswa_id;
    }

    public String getTanggal_bayar() {
        return tanggal_bayar;
    }

    public void setTanggal_bayar(String tanggal_bayar) {
        this.tanggal_bayar = tanggal_bayar;
    }

    public String getBukti_pembayaran() {
        return bukti_pembayaran;
    }

    public void setBukti_pembayaran(String bukti_pembayaran) {
        this.bukti_pembayaran = bukti_pembayaran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJumlah_bayar() {
        return jumlah_bayar;
    }

    public void setJumlah_bayar(String jumlah_bayar) {
        this.jumlah_bayar = jumlah_bayar;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
