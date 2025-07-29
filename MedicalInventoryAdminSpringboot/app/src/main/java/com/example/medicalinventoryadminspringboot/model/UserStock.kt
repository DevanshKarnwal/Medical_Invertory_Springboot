package com.example.medicalinventoryadminspringboot.model

data class UserStock(
    val id: Int = 0,
    val quantity: Int = 0,
    val product: Int? = null  // Only product ID
)
