package com.example.triviagame.api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    //base url for api
    public static final String BASE_URL = "https://jservice.io/api/";
    private ApiUtils() {
    }

    public static RequestAPI getAPIService() {
        int timeOut = 5 * 60;

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestAPI.class);
    }
}
