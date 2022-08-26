package com.example.infotehtestdemo.data.mappers

import com.example.infotehtestdemo.data.local.json.models.Coord
import com.example.infotehtestdemo.domain.models.Coordinates

fun Coord.toCoordinates() : Coordinates = Coordinates(
    lat = lat,
    lon = lon
)