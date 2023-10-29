package com.example.atencinciudadana.ui;

import retrofit2.Call;

import java.util.List;

import retrofit2.http.GET;

public interface ApiService {
    @GET("ruta/a/tu/endpoint")
    Call<List<LocationData>> getLocations();
}

