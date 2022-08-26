package com.example.infotehtestdemo.data.local.json

interface JSONParser<T> {
    suspend fun parse() : List<T>
}