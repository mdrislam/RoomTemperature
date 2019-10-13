package com.eoms.roomtemperature.apiCollection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getRetrofitClient(){

        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}

//                http://api.openweathermap.org/data/2.5/forecast?q=Dhaka,bd&units=imperial&appid=73cf7d86bf96162a451906eb62c84f10
