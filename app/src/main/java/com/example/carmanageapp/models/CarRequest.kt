package com.example.carmanageapp.models

data class CarRequest(
    val title : String,
    val description : String,
    val image : List<String>,
    val tags : List<String>,
)
