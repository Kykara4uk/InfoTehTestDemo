package com.example.infotehtestdemo.data.local.json

import android.content.Context
import android.content.res.AssetManager
import com.example.infotehtestdemo.data.local.json.models.PlaceItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesParser @Inject constructor(private val context: Context) : JSONParser<PlaceItem> {


    override suspend fun parse(): List<PlaceItem> {
        return withContext(Dispatchers.IO){
            val placesStream = getInputStream("city_list.json")
            val jsonReader = JsonReader(InputStreamReader(placesStream, "UTF-8"))
            val placesArray = ArrayList<PlaceItem>()
            jsonReader.beginArray()
            val gson = Gson()
            while (jsonReader.hasNext()){
                val place = gson.fromJson<PlaceItem>(jsonReader, PlaceItem::class.java)
                placesArray.add(place)
            }
            jsonReader.endArray()
            jsonReader.close()
            placesArray.toList()
        }
    }

    private fun getInputStream(fileName: String) = context.assets.open(fileName)

    private fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}
}