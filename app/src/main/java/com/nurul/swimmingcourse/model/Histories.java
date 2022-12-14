package com.nurul.swimmingcourse.model;

import java.util.ArrayList;

public class Histories {
    String id, data_jadwal_periksa_id, tanggal, nama_kucing, jenis_kucing, hasil_diagnosa, saran_pengobatan;
    Users users;
    ArrayList<Gejalas> gejalas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public ArrayList<Gejalas> getGejalas() {
        return gejalas;
    }

    public void setGejalas(ArrayList<Gejalas> gejalas) {
        this.gejalas = gejalas;
    }

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

//    public Users getData() {
//        return data;
//    }
//
//    public void setData(Users data) {
//        this.data = data;
//    }
}
