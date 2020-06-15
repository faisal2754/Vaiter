package com.example.vaiterapp.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://lamp.ms.wits.ac.za/home/s2095208/Vaiter/";
    private static RetrofitClient mInstance;
    private Retrofit retro;

    private RetrofitClient(){
        retro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public API getAPI(){
        return retro.create(API.class);
    }
}
