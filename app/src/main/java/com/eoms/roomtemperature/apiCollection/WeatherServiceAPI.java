package com.eoms.roomtemperature.apiCollection;



import com.eoms.roomtemperature.models.CurrentWeather;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherServiceAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=Dhaka,bd&units=metric&appid=73cf7d86bf96162a451906eb62c84f10
    @GET()
    Call<CurrentWeather>getCurrentWeatherCall(@Url String url);


}
