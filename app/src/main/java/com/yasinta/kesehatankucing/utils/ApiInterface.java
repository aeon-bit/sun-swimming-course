package com.yasinta.kesehatankucing.utils;


import com.yasinta.kesehatankucing.model.Logins;
import com.yasinta.kesehatankucing.model.Registers;
import com.yasinta.kesehatankucing.model.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/login")
    Call<Logins> performLogin(
//            @Header("Authorization") String Token,
            @Field("email") String Email,
            @Field("password") String Password
    );

    @FormUrlEncoded
    @POST("auth/register")
    Call <Registers> performRegistration(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp,
            @Field("nama_kucing") String nama_kucing,
            @Field("jenis_kucing") String jenis_kucing,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String cpassword
    );

}
