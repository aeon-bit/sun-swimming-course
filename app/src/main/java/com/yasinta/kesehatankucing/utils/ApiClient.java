package com.yasinta.kesehatankucing.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static final String BASE_URL = "http://192.168.8.102:8000/";
//    public static final String BASE_URL = "http://192.168.0.200:8000/";
//    public static final String BASE_URL = "http://192.168.43.206:8000/";
//    public static final String BASE_URL = "http://192.168.57.101:8000/";
    public static final String BASE_URL = "https://admin-page-diagnosis-penyakit-kucing.my.id/";

    public static final String IMAGE_URL = BASE_URL + "storage/images/";

    public static final String API = BASE_URL + "api/";

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
