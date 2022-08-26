package com.example.infotehtestdemo.data.remote.api

import com.example.infotehtestdemo.data.remote.api.dto.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherInterface {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ) : CurrentWeatherResponse
}