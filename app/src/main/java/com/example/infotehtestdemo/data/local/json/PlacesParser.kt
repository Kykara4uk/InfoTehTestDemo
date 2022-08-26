package com.example.infotehtestdemo.data.local.json

import android.content.Context
import android.content.res.AssetManager
import com.example.infotehtestdemo.data.local.json.models.PlaceItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesParser @Inject constructor(private val context: Context) : JSONParser<PlaceItem> {


    override suspend fun parse(): List<PlaceItem> {
        return withContext(Dispatchers.IO){
            val placesString = context.assets.readAssetsFile("city_list.json")
            val typeToken = object : TypeToken<List<PlaceItem>>() {}.type
            Gson().fromJson(placesString, typeToken)
        }
    }

    private fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}
}