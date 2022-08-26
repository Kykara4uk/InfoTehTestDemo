package com.example.infotehtestdemo.domain.repository

import com.example.infotehtestdemo.domain.models.CurrentWeather
import com.example.infotehtestdemo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(lat: Double, lon: Double) : Flow<Resource<CurrentWeather>>
}