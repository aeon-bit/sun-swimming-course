package com.nurul.swimmingcourse.utils;


import com.nurul.swimmingcourse.model.Artikels;
import com.nurul.swimmingcourse.model.Logins;
import com.nurul.swimmingcourse.model.Registers;
import com.nurul.swimmingcourse.model.ResponseBookingJadwals;
import com.nurul.swimmingcourse.model.ResponseDetailArtikel;
import com.nurul.swimmingcourse.model.ResponseInfoPerkembangan;
import com.nurul.swimmingcourse.model.ResponseInputPerkembangan;
import com.nurul.swimmingcourse.model.ResponsePembayarans;
import com.nurul.swimmingcourse.model.ResponsePesanJadwal;
import com.nurul.swimmingcourse.model.ResponseSPPelatih;
import com.nurul.swimmingcourse.model.ResponseSPSiswa;
import com.nurul.swimmingcourse.model.ResponseTesKesehatan;
import com.nurul.swimmingcourse.model.UpdateProfiles;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

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

    @FormUrlEncoded
    @PUT
    Call <UpdateProfiles> performUbahProfile(
            @Url String url,
            @Header("Authorization") String token,
            @Field("id") String id,
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

    @GET("informasi")
    Call<Artikels> getAllArtikel(
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("jadwal-latihan")
    Call <ResponsePesanJadwal> performPesanJadwal(
            @Header("Authorization") String token,
            @Field("pelatih_id") String pelatih_id,
            @Field("siswa_id") String siswa_id,
            @Field("hari") String hari,
            @Field("jam") String jam,
            @Field("lokasi") String lokasi
    );

    @FormUrlEncoded
    @POST("perkembangan-siswa")
    Call <ResponseInputPerkembangan> performInputPerkembangan(
            @Header("Authorization") String token,
            @Field("pelatih_id") String pelatih_id,
            @Field("siswa_id") String siswa_id,
            @Field("tanggal_latihan") String tanggal_latihan,
            @Field("lokasi") String lokasi,
            @Field("keterangan") String keterangan
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

    @Multipart
    @POST("pembayaran")
    Call <ResponsePembayarans> performBayar(
            @Header("Authorization") String token,
            @Part("siswa_id") RequestBody siswa_id,
            @Part("tanggal_bayar") RequestBody tanggal_bayar,
            @Part("jumlah_bayar") RequestBody jumlah_bayar,
            @Part MultipartBody.Part bukti_pembayaran
    );

    @FormUrlEncoded
    @POST("data-jadwal-periksa/tambah-data")
    Call <ResponseBookingJadwals> performBookingJadwal(
            @Header("Authorization") String token,
            @Field("tanggal_periksa") String tanggal_periksa,
            @Field("nama_kucing") String nama_kucing,
            @Field("jenis_kucing") String jenis_kucing
    );

    @GET("pelatih")
    Call <ResponseSPPelatih> getSpAllPelatih(
            @Header("Authorization") String token
    );

    @GET("siswa")
    Call <ResponseSPSiswa> getSpAllSiswa(
            @Header("Authorization") String token
    );

    @GET("perkembangan-siswa")
    Call <ResponseInfoPerkembangan> getAllPerkembangan(
            @Header("Authorization") String token
    );

    @GET("perkembangan-siswa/{id}")
    Call <ResponseInfoPerkembangan> getAllPerkembanganByIdSiswa(
            @Header("Authorization") String token,
            @Path("id") String id
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
