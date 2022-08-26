package com.example.infotehtestdemo.data.remote.api.dto


import com.google.gson.annotations.SerializedName

data class CloudsResponse(
    @SerializedName("all")
    val all: Int
)