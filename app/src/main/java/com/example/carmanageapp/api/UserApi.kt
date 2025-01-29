package com.example.carmanageapp.api

import com.example.carmanageapp.models.UserRequest
import com.example.carmanageapp.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/user/signup")
    suspend fun signup(@Body request: UserRequest): Response<UserResponse>

    @POST("/user/signin")
    suspend fun signin(@Body request: UserRequest): Response<UserResponse>
}