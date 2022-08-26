package com.example.infotehtestdemo.data.remote.api.dto


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: CloudsResponse,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: CoordResponse,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: MainResponse,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: SysResponse,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<WeatherResponse>,
    @SerializedName("wind")
    val wind: WindResponse
)