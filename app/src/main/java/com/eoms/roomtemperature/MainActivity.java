package com.eoms.roomtemperature;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.eoms.roomtemperature.apiCollection.RetrofitClient;
import com.eoms.roomtemperature.apiCollection.WeatherServiceAPI;
import com.eoms.roomtemperature.models.CurrentWeather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG ="main " ;
    private FusedLocationProviderClient providerClient;

    private WeatherServiceAPI weatherServiceAPI;


    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private TextView cityNametTv,dateTimeTv,tempTv,weatherDetailTv;
    private ImageView tempTypeIV;
    private ToggleButton toggle_btn;
    private String units="C";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view initialize
        findViewByID();

        providerClient =
                LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location : locationResult.getLocations()){
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    getCurrentWeather(latitude,longitude,"metric");
                    setLesenner(latitude,longitude);



                }
            }
        };



    }

    private LocationRequest getLocaionRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        return locationRequest;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkLocationPermission()){
          //  getDeviceLastLocation();
            getDeviceCurrentLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        providerClient.removeLocationUpdates(locationCallback);
    }

    private boolean checkLocationPermission(){
        if(ActivityCompat
                .checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    555
            );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 555
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           // getDeviceLastLocation();
            getDeviceCurrentLocation();
        }else{
            Toast.makeText(
                    this,
                    "Permission denied",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getDeviceCurrentLocation(){
        if(checkLocationPermission()){
            providerClient.requestLocationUpdates(getLocaionRequest()
                    ,locationCallback,null);
        }
    }






    //getWeather by lat lon
    private void getCurrentWeather(Double lat,Double lon,String type) {


        if(lat!=null&& lon!=null){

            Log.e(TAG,"Lat="+lat+", long="+lon);

           String url="weather?lat="+lat+"&lon="+lon+"&units="+type+"&appid=73cf7d86bf96162a451906eb62c84f10";

             //String url="weather?q="+"dhaka"+"&units=metric&appid=73cf7d86bf96162a451906eb62c84f10";

            weatherServiceAPI= RetrofitClient.getRetrofitClient().create(WeatherServiceAPI.class);
            Call<CurrentWeather> currentWeatherCall=weatherServiceAPI.getCurrentWeatherCall(url);

            currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {

                    CurrentWeather weather=response.body();

                    Log.e(TAG,"werwe== "+weather);

                    if(weather !=null){

                       cityNametTv.setText(weather.getName() + "," + weather.getSys().getCountry());
                        dateTimeTv.setText(getActualDate(weather.getDt()).toString());
                        tempTv.setText(String.valueOf(weather.getMain().getTemp()) + "°" + units);

                    }

                }

                @Override
                public void onFailure(Call<CurrentWeather> call, Throwable t) {

                    Log.e(TAG,"Error: "+t.getMessage());

                }
            });


        }


    }


    //Toggle button
    private void setLesenner(final Double lat, final Double lon) {

        toggle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle_btn.isChecked()){
                   // getCurrentWeather("weather?lat="+lat+"&lon="+lon+"&units=metric&appid=73cf7d86bf96162a451906eb62c84f10");
                   getCurrentWeather(lat,lon,"metric");
                    units="C";
                }
                else {
                   // getCurrentWeather( "weather?lat="+lat+"&lon="+lon+"&units=imperial&appid=73cf7d86bf96162a451906eb62c84f10");
                    getCurrentWeather(lat,lon,"imperial");

                    units="F";

                }
            }
        });
    }

    //formating date
    private String getActualDate(int timestamp){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp*1000L);
        String date = DateFormat.format("EEE, MMM d, yyyy", calendar).toString();
        return date;
    }

    private void findViewByID() {

         cityNametTv=findViewById(R.id.cityNameTv);
        dateTimeTv=findViewById(R.id.dateTimeTv);
        tempTv=findViewById(R.id.tempTv);
        weatherDetailTv=findViewById(R.id.weatherDetailTv);

        tempTypeIV=findViewById(R.id.tempTypeTV);

        toggle_btn=findViewById(R.id.toggle_btn);

    }

}
