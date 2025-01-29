package com.example.carmanageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carmanageapp.models.CarRequest
import com.example.carmanageapp.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(private val carRepository: CarRepository) : ViewModel() {

    val carLiveData get() = carRepository.carLiveData
    val statusLiveData get() = carRepository.statusLiveData

    fun getCars(){
        viewModelScope.launch {
            carRepository.getCars()
        }

    }

    fun createCar(carRequest: CarRequest){
        viewModelScope.launch {
            carRepository.createCar(carRequest)
        }

    }

    fun deleteCar(carId : String){
        viewModelScope.launch {
            carRepository.deleteCar(carId)
        }
    }

    fun updateCar(carId : String, carRequest: CarRequest){
        viewModelScope.launch {
            carRepository.updateCar(carId, carRequest)
        }
    }


    fun searchCars(keyword : String){
        viewModelScope.launch {
            carRepository.searchCars(keyword)
        }

    }


}