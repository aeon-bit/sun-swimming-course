package com.nurul.swimmingcourse.utils;


import com.nurul.swimmingcourse.model.Artikels;
import com.nurul.swimmingcourse.model.Logins;
import com.nurul.swimmingcourse.model.Registers;
import com.nurul.swimmingcourse.model.ResponseBookingJadwals;
import com.nurul.swimmingcourse.model.ResponseDetailArtikel;
import com.nurul.swimmingcourse.model.ResponseSpJadwals;
import com.nurul.swimmingcourse.model.ResponseTesKesehatan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<Logins> performLogin(
//            @Header("Authorization") String Token,
            @Field("username") String Username,
            @Field("password") String Password
    );

    @FormUrlEncoded
    @POST("register")
    Call <Registers> performRegistration(
            @Field("nama") String nama,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("nama_ortu") String nama_ortu,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("data-artikel")
    Call<Artikels> getAllArtikel(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("diagnosa")
    Call <ResponseTesKesehatan> performTesKesehatan(
            @Header("Authorization") String token,
            @Field("nama_kucing") String nama_kucing,
            @Field("jenis_kucing") String jenis_kucing,
            @Field("tanggal") String today,
            @Field("gejala[]") ArrayList<String> gejala
//            @Field("data_jadwal_periksa_id") String data_jadwal_periksa_id
    );

    @FormUrlEncoded
    @POST("data-jadwal-periksa/tambah-data")
    Call <ResponseBookingJadwals> performBookingJadwal(
            @Header("Authorization") String token,
            @Field("tanggal_periksa") String tanggal_periksa,
            @Field("nama_kucing") String nama_kucing,
            @Field("jenis_kucing") String jenis_kucing
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

    @GET("data-artikel/{id}/show")
    Call <ResponseDetailArtikel> getDetailArtikel(
            @Header("Authorization") String token,
            @Path("id") String id
    );

}
