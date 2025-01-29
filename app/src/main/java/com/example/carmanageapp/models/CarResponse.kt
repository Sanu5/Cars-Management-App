package com.example.carmanageapp.models

data class CarResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val image: List<String>,
    val tags: List<String>,
    val title: String,
    val updatedAt: String,
    val userId: String
)