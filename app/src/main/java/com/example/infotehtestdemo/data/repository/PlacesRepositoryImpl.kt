package com.example.infotehtestdemo.data.repository

import com.example.infotehtestdemo.data.local.json.JSONParser
import com.example.infotehtestdemo.data.mappers.toPlaceListing
import com.example.infotehtestdemo.data.local.json.models.PlaceItem
import com.example.infotehtestdemo.domain.models.PlaceListing

import com.example.infotehtestdemo.domain.repository.PlacesRepository
import com.example.infotehtestdemo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesRepositoryImpl @Inject constructor(
    private val placesParser: JSONParser<PlaceItem>
) : PlacesRepository{

    private val placesList = mutableListOf<PlaceListing>()
    override fun getPlaces(query: String): Flow<Resource<List<PlaceListing>>> {
        return flow {
            emit(Resource.Loading(true))
            if (placesList.size==0) {
                val places = try {
                    placesParser.parse()
                } catch (e: Exception) {
                    emit(Resource.Error("Couldn't load data"))
                    null
                }
                places?.let {
                    placesList.addAll(places.map { it.toPlaceListing() })
                }
            }

            emit(Resource.Loading(false))
            if (query.isBlank()){
                    emit(Resource.Success(data = placesList))
                    return@flow
            } else {
                    emit(Resource.Success(data = placesList.filter { it.name.contains(query) }))
                    return@flow
            }


        }
    }
}