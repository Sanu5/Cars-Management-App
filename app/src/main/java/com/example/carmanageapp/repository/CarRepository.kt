package com.example.carmanageapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carmanageapp.api.CarApi
import com.example.carmanageapp.models.CarRequest
import com.example.carmanageapp.models.CarResponse
import com.example.carmanageapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class CarRepository @Inject constructor(private val carApi: CarApi) {

    private val _carLiveData = MutableLiveData<NetworkResult<List<CarResponse>>>()
    val carLiveData: LiveData<NetworkResult<List<CarResponse>>> get() = _carLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>> get() = _statusLiveData

    suspend fun getCars(){
        _carLiveData.postValue(NetworkResult.Loading())
        val response = carApi.getCars()

        if (response.isSuccessful && response.body() != null) {
            _carLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _carLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))

        } else {
            _carLiveData.postValue(NetworkResult.Error("Something went wrong"))

        }
    }

    suspend fun createCar(carRequest: CarRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = carApi.createCar(carRequest)

        handleResponse(response, "Car created")

    }

    suspend fun deleteCar(carId : String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = carApi.deleteCar(carId)

        if(response.isSuccessful && response.body() != null)
        {
            val message = response.body() ?: "Something went wrong"
            _statusLiveData.postValue(NetworkResult.Success(message))
        }
        else{
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun updateCar(carId: String, carRequest: CarRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = carApi.updateCar(carId, carRequest)
        handleResponse(response, "Car Updated")
    }

    suspend fun searchCars(keyword : String){
        _carLiveData.postValue(NetworkResult.Loading())
        val response = carApi.searchCars(keyword)

        if(response.isSuccessful && response.body() != null){
            _carLiveData.postValue(NetworkResult.Success(response.body()!!))

        }
        else{
            _carLiveData.postValue(NetworkResult.Error("Something went wrong"))

        }
    }

    private fun handleResponse(response: Response<CarResponse>, message: String){
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))

        }
    }
}