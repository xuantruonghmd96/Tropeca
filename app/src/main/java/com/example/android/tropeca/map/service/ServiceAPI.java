package com.example.android.tropeca.map.service;

import com.example.android.tropeca.R;
import com.example.android.tropeca.map.service.pojo.GeocodingRoot;

import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceAPI {
    @GET("geocode/json?&key=AIzaSyA1oZSKbPSKIojy2ot4p0ynknYWgz8Z_vQ")
    Call<GeocodingRoot> getLocation(@Query("address") String address);

}
