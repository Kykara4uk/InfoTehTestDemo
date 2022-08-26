package com.example.infotehtestdemo.domain.repository

import com.example.infotehtestdemo.domain.models.PlaceListing
import com.example.infotehtestdemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

fun getPlaces(query: String) : Flow<Resource<List<PlaceListing>>>
}