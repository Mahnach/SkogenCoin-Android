package com.skogen.coin.api;


import android.testcurrencies.com.api.RetrofitApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String TAG = RetrofitService.class.getSimpleName();

    private static RetrofitService retrofitService;

    private static final String BASE_URL = "https://radiant-forest-47611.herokuapp.com/api/";

    private Retrofit retrofit;

    /**
     * returns Base Retrofit Service
     *
     * @return RetrofitApi
     */
    public static RetrofitApi getService() {
        if (retrofitService == null)
            retrofitService = new RetrofitService();
        return retrofitService.retrofit.create(RetrofitApi.class);
    }

    /**
     * constructor
     */
    public RetrofitService() {
        createRetrofit();
    }

    private void createRetrofit() {
        //enable logging
        HttpLoggingInterceptor interceptorLogging = new HttpLoggingInterceptor();
        interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //attach auth header to all requests
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptorLogging)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
