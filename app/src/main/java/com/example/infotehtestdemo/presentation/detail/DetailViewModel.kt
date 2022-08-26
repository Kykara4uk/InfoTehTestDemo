package com.example.infotehtestdemo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infotehtestdemo.domain.models.CurrentWeather
import com.example.infotehtestdemo.domain.models.PlaceListing
import com.example.infotehtestdemo.domain.repository.WeatherRepository
import com.example.infotehtestdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _place = MutableLiveData<PlaceListing>()
    val place: LiveData<PlaceListing>
        get() = _place

    private val _weather = MutableLiveData<CurrentWeather>()
    val weather: LiveData<CurrentWeather>
        get() = _weather



    fun setPlace(placeListing: PlaceListing) {
        _place.postValue(placeListing)
        getWeather(placeListing.coordinates.lat, placeListing.coordinates.lon)
    }

    fun getWeather(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository
                .getWeather(lat, lon)
                .collect{ result->
                    when(result){
                        is Resource.Success -> {
                            result.data?.let {
                                _weather.postValue(it)
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