package com.example.infotehtestdemo.di

import android.content.Context
import com.example.infotehtestdemo.data.local.json.PlacesParser
import com.example.infotehtestdemo.data.remote.api.OpenWeatherClient
import com.example.infotehtestdemo.data.remote.api.OpenWeatherInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun providePlacesParser(@ApplicationContext context: Context) : PlacesParser = PlacesParser(context = context)

    @Provides @Singleton
    fun provideApiService():OpenWeatherInterface{
        return OpenWeatherClient.getClient()
    }
}

