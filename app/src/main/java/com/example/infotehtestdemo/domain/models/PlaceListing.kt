package com.example.infotehtestdemo.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceListing(
    val coordinates: Coordinates,
    val name: String,
) : Parcelable
