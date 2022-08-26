package com.example.infotehtestdemo.di

import com.example.infotehtestdemo.data.local.json.JSONParser
import com.example.infotehtestdemo.data.local.json.PlacesParser
import com.example.infotehtestdemo.data.local.json.models.PlaceItem
import com.example.infotehtestdemo.data.repository.PlacesRepositoryImpl
import com.example.infotehtestdemo.data.repository.WeatherRepositoryImpl
import com.example.infotehtestdemo.domain.repository.PlacesRepository
import com.example.infotehtestdemo.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    @Singleton
    abstract fun bindPlacesRepository(placesRepositoryImpl: PlacesRepositoryImpl) : PlacesRepository

    @Binds
    @Singleton
    abstract fun bindPlacesParser(placesParser: PlacesParser) : JSONParser<PlaceItem>

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository
}