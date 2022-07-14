package com.yasinta.kesehatankucing.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static final String BASE_URL = "http://192.168.0.200/mpasi/";
    public static final String BASE_URL = "http://192.168.8.100:8000/";

    public static final String IMAGE_URL = BASE_URL + "uploaded/";

    public static final String API = BASE_URL + "api/";
    public static final String LOGIN = API + "auth/login/";

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
