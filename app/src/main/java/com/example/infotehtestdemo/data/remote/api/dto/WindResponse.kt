package com.example.infotehtestdemo.data.remote.api.dto


import com.google.gson.annotations.SerializedName

data class WindResponse(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("speed")
    val speed: Double
)