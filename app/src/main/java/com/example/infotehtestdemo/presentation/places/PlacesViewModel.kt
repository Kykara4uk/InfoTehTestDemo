package com.example.infotehtestdemo.presentation.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infotehtestdemo.domain.models.PlaceListing
import com.example.infotehtestdemo.domain.repository.PlacesRepository
import com.example.infotehtestdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _places = MutableLiveData<List<PlaceListing>>()
    val places: LiveData<List<PlaceListing>>
        get() = _places

    private var searchJob : Job? = null

    init {
        getPlacesListing()
    }

    fun searchPlaces(query: String = ""){
        _searchQuery.postValue(query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1)
            getPlacesListing()
        }
    }



    private fun getPlacesListing(query: String = searchQuery.value?.lowercase(Locale.ROOT) ?:""){
        viewModelScope.launch {
            placesRepository
                .getPlaces(query)
                .collect{ result->
                    when(result){
                        is Resource.Success -> {
                            result.data?.let {
                                _places.postValue(it)
                            }
                        }
                        is Resource.Error -> {
                            result.message?.let { _error.emit(it) }
                        }
                        is Resource.Loading -> {
                            _isLoading.postValue(result.isLoading)
                        }
                    }

                }
        }
    }

}