package com.example.infotehtestdemo.data.repository

import com.example.infotehtestdemo.data.mappers.toCurrentWeather
import com.example.infotehtestdemo.data.remote.api.OpenWeatherInterface
import com.example.infotehtestdemo.domain.models.CurrentWeather
import com.example.infotehtestdemo.domain.repository.WeatherRepository
import com.example.infotehtestdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor (
    private val apiService: OpenWeatherInterface,
        ) : WeatherRepository {
    override fun getWeather(lat: Double, lon: Double): Flow<Resource<CurrentWeather>> {
        return flow {
            emit(Resource.Loading(true))
            val currentWeatherResponse = try {
                apiService.getWeather(lat, lon)
            } catch (e: Exception){
                println(e)
                emit(Resource.Error("Couldn't load data"))
                null
            }

            emit(Resource.Loading(false))
            emit(Resource.Success(data = currentWeatherResponse?.toCurrentWeather()))
            return@flow



        }
    }
}