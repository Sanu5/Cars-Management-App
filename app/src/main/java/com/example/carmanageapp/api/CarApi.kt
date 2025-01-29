package com.example.carmanageapp.api

import com.example.carmanageapp.models.CarRequest
import com.example.carmanageapp.models.CarResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CarApi {

    @GET("/car")
    suspend fun getCars(): Response<List<CarResponse>>

    @POST("/car")
    suspend fun createCar(@Body carRequest : CarRequest): Response<CarResponse>

    @PUT("/car/{id}")
    suspend fun updateCar(@Path("id") carId : String, @Body carRequest : CarRequest): Response<CarResponse>

    @DELETE("/car/{id}")
    suspend fun deleteCar(@Path("id") carId : String): Response<String>

    @GET("/car/search")
    suspend fun searchCars(@Query("keyword") query : String): Response<List<CarResponse>>
}