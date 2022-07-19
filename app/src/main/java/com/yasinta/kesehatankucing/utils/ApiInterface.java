package com.yasinta.kesehatankucing.utils;


import com.yasinta.kesehatankucing.model.Artikels;
import com.yasinta.kesehatankucing.model.Gejalas;
import com.yasinta.kesehatankucing.model.JadwalPeriksas;
import com.yasinta.kesehatankucing.model.Logins;
import com.yasinta.kesehatankucing.model.Registers;
import com.yasinta.kesehatankucing.model.ResponseBookingJadwals;
import com.yasinta.kesehatankucing.model.ResponseSpJadwals;
import com.yasinta.kesehatankucing.model.ResponseTesKesehatan;
import com.yasinta.kesehatankucing.model.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<Logins> performLogin(
//            @Header("Authorization") String Token,
            @Field("email") String Email,
            @Field("password") String Password
    );

    @FormUrlEncoded
    @POST("register")
    Call <Registers> performRegistration(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp,
            @Field("nama_kucing") String nama_kucing,
            @Field("jenis_kucing") String jenis_kucing,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String cpassword
    );

    @GET("data-artikel")
    Call<Artikels> getAllArtikel(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("diagnosa")
    Call <ResponseTesKesehatan> performTesKesehatan(
            @Header("Authorization") String token,
            @Field("gejala[]") ArrayList<String> gejala,
            @Field("data_jadwal_periksa_id") String data_jadwal_periksa_id
    );

    @FormUrlEncoded
    @POST("data-jadwal-periksa/tambah-data")
    Call <ResponseBookingJadwals> performBookingJadwal(
            @Header("Authorization") String token,
            @Field("tanggal_periksa") String tanggal_periksa
    );

    @GET("data-jadwal-periksa")
    Call <ResponseSpJadwals> getSpJadwalPeriksa(
            @Header("Authorization") String token
    );

    @GET("data-riwayat-diagnosa/{id}/show")
    Call <ResponseTesKesehatan> getDetailDiagnosa(
            @Header("Authorization") String token,
            @Path("id") String id
    );

}
