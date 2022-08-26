package com.example.infotehtestdemo.domain.models

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val description: String,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val humidity: Int,
    val windSpeed: Double
)
