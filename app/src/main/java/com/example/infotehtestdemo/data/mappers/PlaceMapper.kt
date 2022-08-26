package com.example.infotehtestdemo.data.mappers

import com.example.infotehtestdemo.data.local.json.models.PlaceItem
import com.example.infotehtestdemo.domain.models.PlaceListing

fun PlaceItem.toPlaceListing() : PlaceListing = PlaceListing(
    name = name,
    coordinates = coordinates.toCoordinates()
)