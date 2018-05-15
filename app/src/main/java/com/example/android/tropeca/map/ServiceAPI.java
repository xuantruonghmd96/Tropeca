package com.example.android.tropeca.map;

import com.example.android.tropeca.map.direction.DirectionRoot;
import com.example.android.tropeca.map.geocoding.GeocodingRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceAPI {
    @GET("geocode/json?&key=AIzaSyA1oZSKbPSKIojy2ot4p0ynknYWgz8Z_vQ")
    Call<GeocodingRoot> getLocation(@Query("address") String address);

    @GET("directions/json?&key=AIzaSyA1oZSKbPSKIojy2ot4p0ynknYWgz8Z_vQ")
    Call<DirectionRoot> getDirection(@Query("origin") String origin,
                                     @Query("destination") String destination);

}
