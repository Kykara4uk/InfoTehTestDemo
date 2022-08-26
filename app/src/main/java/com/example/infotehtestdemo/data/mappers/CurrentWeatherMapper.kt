package com.example.infotehtestdemo.data.mappers

import com.example.infotehtestdemo.data.remote.api.dto.CurrentWeatherResponse
import com.example.infotehtestdemo.domain.models.CurrentWeather

fun CurrentWeatherResponse.toCurrentWeather() : CurrentWeather = CurrentWeather(
    description= weather[0].description,
    temp= main.temp,
    tempMax= main.tempMax,
    tempMin= main.tempMin,
    humidity= main.humidity,
    windSpeed= wind.speed
)